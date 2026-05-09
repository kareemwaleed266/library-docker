package com.library.circulation.mapper;

import com.library.circulation.dto.response.BorrowTransactionResponse;
import com.library.circulation.entity.BorrowTransaction;
import org.springframework.stereotype.Component;

@Component
public class BorrowTransactionMapper {

    public BorrowTransactionResponse toResponse(BorrowTransaction transaction) {
        return new BorrowTransactionResponse(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getBookId(),
                transaction.getStatus(),
                transaction.getBorrowDate(),
                transaction.getDueDate(),
                transaction.getReturnedDate(),
                transaction.getFineAmount(),
                transaction.getApprovedBy(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}
