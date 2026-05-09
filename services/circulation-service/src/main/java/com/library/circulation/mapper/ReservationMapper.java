package com.library.circulation.mapper;

import com.library.circulation.dto.response.ReservationResponse;
import com.library.circulation.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getBookId(),
                reservation.getStatus(),
                reservation.getQueueOrder(),
                reservation.getReservationDate(),
                reservation.getExpiryDate(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
    }
}
