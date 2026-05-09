package com.library.circulation.service;

import com.library.circulation.dto.request.ApproveBorrowRequest;
import com.library.circulation.dto.request.BorrowRequest;
import com.library.circulation.dto.response.BorrowTransactionResponse;
import com.library.common.security.CurrentUser;

import java.util.List;
import java.util.UUID;

public interface BorrowService {

    BorrowTransactionResponse requestBorrow(CurrentUser currentUser, BorrowRequest request);

    List<BorrowTransactionResponse> getMyBorrows(CurrentUser currentUser);

    List<BorrowTransactionResponse> getAllBorrows();

    BorrowTransactionResponse approve(UUID borrowId, CurrentUser currentUser, ApproveBorrowRequest request);

    BorrowTransactionResponse reject(UUID borrowId, CurrentUser currentUser);

    BorrowTransactionResponse returnBook(UUID borrowId);
}
