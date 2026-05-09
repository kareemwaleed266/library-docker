package com.library.circulation.controller;

import com.library.circulation.dto.request.ApproveBorrowRequest;
import com.library.circulation.dto.request.BorrowRequest;
import com.library.circulation.dto.response.BorrowTransactionResponse;
import com.library.circulation.service.BorrowService;
import com.library.common.dto.ApiResponse;
import com.library.common.security.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BorrowTransactionResponse> requestBorrow(
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody BorrowRequest request
    ) {
        return ApiResponse.success(
                "Borrow request created successfully",
                borrowService.requestBorrow(currentUser, request)
        );
    }

    @GetMapping("/my")
    public ApiResponse<List<BorrowTransactionResponse>> getMyBorrows(
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "My borrow transactions retrieved successfully",
                borrowService.getMyBorrows(currentUser)
        );
    }

    @GetMapping
    public ApiResponse<List<BorrowTransactionResponse>> getAllBorrows() {
        return ApiResponse.success(
                "Borrow transactions retrieved successfully",
                borrowService.getAllBorrows()
        );
    }

    @PatchMapping("/{borrowId}/approve")
    public ApiResponse<BorrowTransactionResponse> approve(
            @PathVariable UUID borrowId,
            @AuthenticationPrincipal CurrentUser currentUser,
            @Valid @RequestBody ApproveBorrowRequest request
    ) {
        return ApiResponse.success(
                "Borrow request approved successfully",
                borrowService.approve(borrowId, currentUser, request)
        );
    }

    @PatchMapping("/{borrowId}/reject")
    public ApiResponse<BorrowTransactionResponse> reject(
            @PathVariable UUID borrowId,
            @AuthenticationPrincipal CurrentUser currentUser
    ) {
        return ApiResponse.success(
                "Borrow request rejected successfully",
                borrowService.reject(borrowId, currentUser)
        );
    }

    @PatchMapping("/{borrowId}/return")
    public ApiResponse<BorrowTransactionResponse> returnBook(
            @PathVariable UUID borrowId
    ) {
        return ApiResponse.success(
                "Book returned successfully",
                borrowService.returnBook(borrowId)
        );
    }
}
