package com.library.circulation.repository;

import com.library.circulation.entity.BorrowTransaction;
import com.library.circulation.enums.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BorrowTransactionRepository extends JpaRepository<BorrowTransaction, UUID> {

    List<BorrowTransaction> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<BorrowTransaction> findByStatus(BorrowStatus status);

    Optional<BorrowTransaction> findByUserIdAndBookIdAndStatus(UUID userId, UUID bookId, BorrowStatus status);

    boolean existsByUserIdAndBookIdAndStatusIn(UUID userId, UUID bookId, List<BorrowStatus> statuses);

    long countByStatus(BorrowStatus status);
}
