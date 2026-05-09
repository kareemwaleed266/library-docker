package com.library.circulation.controller;

import com.library.circulation.dto.request.ReservationRequest;
import com.library.circulation.dto.response.ReservationResponse;
import com.library.circulation.service.ReservationService;
import com.library.common.dto.ApiResponse;
import com.library.common.security.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationResponse> reserve(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody ReservationRequest request
    ) {
        return ApiResponse.success(
                "Reservation created successfully",
                reservationService.reserve(currentUser, request)
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<ReservationResponse>> getMyReservations(
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "My reservations retrieved successfully",
                reservationService.getMyReservations(currentUser)
        );
    }

    @GetMapping("/book/{bookId}")
    public ApiResponse<List<ReservationResponse>> getReservationsByBook(
            @PathVariable UUID bookId
    ) {
        return ApiResponse.success(
                "Book reservations retrieved successfully",
                reservationService.getReservationsByBook(bookId)
        );
    }

    @PatchMapping("/{reservationId}/cancel")
    public ApiResponse<ReservationResponse> cancel(
            @PathVariable UUID reservationId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "Reservation cancelled successfully",
                reservationService.cancel(reservationId, currentUser)
        );
    }
}
