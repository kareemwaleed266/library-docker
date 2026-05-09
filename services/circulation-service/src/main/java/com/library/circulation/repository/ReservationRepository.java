package com.library.circulation.repository;

import com.library.circulation.entity.Reservation;
import com.library.circulation.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<Reservation> findByBookIdAndStatusOrderByQueueOrderAsc(UUID bookId, ReservationStatus status);

    boolean existsByUserIdAndBookIdAndStatus(UUID userId, UUID bookId, ReservationStatus status);

    int countByBookIdAndStatus(UUID bookId, ReservationStatus status);

    long countByStatus(ReservationStatus status);
}
