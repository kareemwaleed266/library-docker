package com.library.circulation.service.impl;

import com.library.circulation.client.CatalogClient;
import com.library.circulation.dto.request.ApproveBorrowRequest;
import com.library.circulation.dto.request.BorrowRequest;
import com.library.circulation.dto.response.BorrowTransactionResponse;
import com.library.circulation.entity.BorrowTransaction;
import com.library.circulation.enums.BorrowStatus;
import com.library.circulation.mapper.BorrowTransactionMapper;
import com.library.circulation.repository.BorrowTransactionRepository;
import com.library.circulation.service.BorrowService;
import com.library.common.exception.BadRequestException;
import com.library.common.exception.NotFoundException;
import com.library.common.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BorrowServiceImpl implements BorrowService {

    private static final BigDecimal DAILY_FINE_AMOUNT = BigDecimal.valueOf(5);

    private final BorrowTransactionRepository borrowRepository;
    private final BorrowTransactionMapper borrowMapper;
    private final CatalogClient catalogClient;

    public BorrowServiceImpl(
            BorrowTransactionRepository borrowRepository,
            BorrowTransactionMapper borrowMapper,
            CatalogClient catalogClient
    ) {
        this.borrowRepository = borrowRepository;
        this.borrowMapper = borrowMapper;
        this.catalogClient = catalogClient;
    }

    @Override
    public BorrowTransactionResponse requestBorrow(CurrentUser currentUser, BorrowRequest request) {
        boolean hasActiveBorrow = borrowRepository.existsByUserIdAndBookIdAndStatusIn(
                currentUser.id(),
                request.bookId(),
                List.of(BorrowStatus.PENDING, BorrowStatus.APPROVED, BorrowStatus.OVERDUE)
        );

        if (hasActiveBorrow) {
            throw new BadRequestException("You already have an active borrow request for this book");
        }

        BorrowTransaction transaction = new BorrowTransaction(
                currentUser.id(),
                request.bookId()
        );

        return borrowMapper.toResponse(borrowRepository.save(transaction));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowTransactionResponse> getMyBorrows(CurrentUser currentUser) {
        return borrowRepository.findByUserIdOrderByCreatedAtDesc(currentUser.id())
                .stream()
                .map(borrowMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowTransactionResponse> getAllBorrows() {
        return borrowRepository.findAll()
                .stream()
                .map(borrowMapper::toResponse)
                .toList();
    }

    @Override
    public BorrowTransactionResponse approve(
            UUID borrowId,
            CurrentUser currentUser,
            ApproveBorrowRequest request
    ) {
        BorrowTransaction transaction = findBorrowById(borrowId);

        if (transaction.getStatus() != BorrowStatus.PENDING) {
            throw new BadRequestException("Only pending borrow requests can be approved");
        }

        catalogClient.decreaseAvailableCopy(transaction.getBookId());

        Instant borrowDate = Instant.now();
        Instant dueDate = borrowDate.plus(Duration.ofDays(request.borrowDurationDays()));

        transaction.approve(currentUser.id(), borrowDate, dueDate);

        return borrowMapper.toResponse(transaction);
    }

    @Override
    public BorrowTransactionResponse reject(UUID borrowId, CurrentUser currentUser) {
        BorrowTransaction transaction = findBorrowById(borrowId);

        if (transaction.getStatus() != BorrowStatus.PENDING) {
            throw new BadRequestException("Only pending borrow requests can be rejected");
        }

        transaction.reject(currentUser.id());

        return borrowMapper.toResponse(transaction);
    }

    @Override
    public BorrowTransactionResponse returnBook(UUID borrowId) {
        BorrowTransaction transaction = findBorrowById(borrowId);

        if (transaction.getStatus() != BorrowStatus.APPROVED && transaction.getStatus() != BorrowStatus.OVERDUE) {
            throw new BadRequestException("Only approved or overdue borrows can be returned");
        }

        catalogClient.increaseAvailableCopy(transaction.getBookId());

        Instant returnedDate = Instant.now();
        BigDecimal fineAmount = calculateFine(transaction.getDueDate(), returnedDate);

        transaction.markReturned(returnedDate, fineAmount);

        return borrowMapper.toResponse(transaction);
    }

    private BorrowTransaction findBorrowById(UUID borrowId) {
        return borrowRepository.findById(borrowId)
                .orElseThrow(() -> new NotFoundException("Borrow transaction not found"));
    }

    private BigDecimal calculateFine(Instant dueDate, Instant returnedDate) {
        if (dueDate == null || !returnedDate.isAfter(dueDate)) {
            return BigDecimal.ZERO;
        }

        long lateDays = Math.max(1, Duration.between(dueDate, returnedDate).toDays());

        return DAILY_FINE_AMOUNT.multiply(BigDecimal.valueOf(lateDays));
    }
}
