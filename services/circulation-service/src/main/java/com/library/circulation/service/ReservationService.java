package com.library.circulation.service;

import com.library.circulation.dto.request.ReservationRequest;
import com.library.circulation.dto.response.ReservationResponse;
import com.library.common.security.CurrentUser;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationResponse reserve(CurrentUser currentUser, ReservationRequest request);

    List<ReservationResponse> getMyReservations(CurrentUser currentUser);

    List<ReservationResponse> getReservationsByBook(UUID bookId);

    ReservationResponse cancel(UUID reservationId, CurrentUser currentUser);
}
