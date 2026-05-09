package com.library.circulation.entity;

import com.library.circulation.enums.BorrowStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "borrow_transactions")
@EntityListeners(AuditingEntityListener.class)
public class BorrowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID bookId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BorrowStatus status = BorrowStatus.PENDING;

    private Instant borrowDate;

    private Instant dueDate;

    private Instant returnedDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount = BigDecimal.ZERO;

    private UUID approvedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    protected BorrowTransaction() {
    }

    public BorrowTransaction(UUID userId, UUID bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.status = BorrowStatus.PENDING;
        this.fineAmount = BigDecimal.ZERO;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getBookId() { return bookId; }
    public BorrowStatus getStatus() { return status; }
    public Instant getBorrowDate() { return borrowDate; }
    public Instant getDueDate() { return dueDate; }
    public Instant getReturnedDate() { return returnedDate; }
    public BigDecimal getFineAmount() { return fineAmount; }
    public UUID getApprovedBy() { return approvedBy; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void approve(UUID librarianId, Instant borrowDate, Instant dueDate) {
        this.status = BorrowStatus.APPROVED;
        this.approvedBy = librarianId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public void reject(UUID librarianId) {
        this.status = BorrowStatus.REJECTED;
        this.approvedBy = librarianId;
    }

    public void markReturned(Instant returnedDate, BigDecimal fineAmount) {
        this.status = BorrowStatus.RETURNED;
        this.returnedDate = returnedDate;
        this.fineAmount = fineAmount;
    }

    public void markOverdue() {
        this.status = BorrowStatus.OVERDUE;
    }
}
