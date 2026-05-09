package com.library.circulation.service.impl;

import com.library.circulation.dto.request.ReservationRequest;
import com.library.circulation.dto.response.ReservationResponse;
import com.library.circulation.entity.Reservation;
import com.library.circulation.enums.ReservationStatus;
import com.library.circulation.mapper.ReservationMapper;
import com.library.circulation.repository.ReservationRepository;
import com.library.circulation.service.ReservationService;
import com.library.common.exception.BadRequestException;
import com.library.common.exception.NotFoundException;
import com.library.common.security.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private static final int RESERVATION_EXPIRY_DAYS = 3;

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            ReservationMapper reservationMapper
    ) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public ReservationResponse reserve(CurrentUser currentUser, ReservationRequest request) {
        boolean alreadyReserved = reservationRepository.existsByUserIdAndBookIdAndStatus(
                currentUser.id(),
                request.bookId(),
                ReservationStatus.ACTIVE
        );

        if (alreadyReserved) {
            throw new BadRequestException("You already have an active reservation for this book");
        }

        int queueOrder = reservationRepository.countByBookIdAndStatus(
                request.bookId(),
                ReservationStatus.ACTIVE
        ) + 1;

        Instant reservationDate = Instant.now();
        Instant expiryDate = reservationDate.plus(Duration.ofDays(RESERVATION_EXPIRY_DAYS));

        Reservation reservation = new Reservation(
                currentUser.id(),
                request.bookId(),
                queueOrder,
                reservationDate,
                expiryDate
        );

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getMyReservations(CurrentUser currentUser) {
        return reservationRepository.findByUserIdOrderByCreatedAtDesc(currentUser.id())
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> getReservationsByBook(UUID bookId) {
        return reservationRepository.findByBookIdAndStatusOrderByQueueOrderAsc(
                        bookId,
                        ReservationStatus.ACTIVE
                )
                .stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @Override
    public ReservationResponse cancel(UUID reservationId, CurrentUser currentUser) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found"));

        if (!reservation.getUserId().equals(currentUser.id()) && currentUser.isUser()) {
            throw new BadRequestException("You can only cancel your own reservations");
        }

        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new BadRequestException("Only active reservations can be cancelled");
        }

        reservation.cancel();

        return reservationMapper.toResponse(reservation);
    }
}
