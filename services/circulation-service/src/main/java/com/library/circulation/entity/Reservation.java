package com.library.circulation.entity;

import com.library.circulation.enums.ReservationStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID bookId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReservationStatus status = ReservationStatus.ACTIVE;

    @Column(nullable = false)
    private int queueOrder;

    @Column(nullable = false)
    private Instant reservationDate;

    private Instant expiryDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    protected Reservation() {
    }

    public Reservation(UUID userId, UUID bookId, int queueOrder, Instant reservationDate, Instant expiryDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.queueOrder = queueOrder;
        this.reservationDate = reservationDate;
        this.expiryDate = expiryDate;
        this.status = ReservationStatus.ACTIVE;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getBookId() { return bookId; }
    public ReservationStatus getStatus() { return status; }
    public int getQueueOrder() { return queueOrder; }
    public Instant getReservationDate() { return reservationDate; }
    public Instant getExpiryDate() { return expiryDate; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void fulfill() {
        this.status = ReservationStatus.FULFILLED;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
    }

    public void expire() {
        this.status = ReservationStatus.EXPIRED;
    }
}
