package com.library.circulation.controller.internal;

import com.library.circulation.dto.internal.CirculationStatsResponse;
import com.library.circulation.enums.BorrowStatus;
import com.library.circulation.enums.ReservationStatus;
import com.library.circulation.repository.BorrowTransactionRepository;
import com.library.circulation.repository.ReservationRepository;
import com.library.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/reports/circulation")
public class InternalCirculationReportController {

    private final BorrowTransactionRepository borrowRepository;
    private final ReservationRepository reservationRepository;

    public InternalCirculationReportController(
            BorrowTransactionRepository borrowRepository,
            ReservationRepository reservationRepository
    ) {
        this.borrowRepository = borrowRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public ApiResponse<CirculationStatsResponse> getCirculationStats() {
        return ApiResponse.success(
                "Circulation stats retrieved successfully",
                new CirculationStatsResponse(
                        borrowRepository.count(),
                        borrowRepository.countByStatus(BorrowStatus.PENDING),
                        borrowRepository.countByStatus(BorrowStatus.APPROVED),
                        borrowRepository.countByStatus(BorrowStatus.RETURNED),
                        borrowRepository.countByStatus(BorrowStatus.OVERDUE),
                        reservationRepository.countByStatus(ReservationStatus.ACTIVE)
                )
        );
    }
}
