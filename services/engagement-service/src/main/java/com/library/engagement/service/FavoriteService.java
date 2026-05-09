package com.library.engagement.service;

import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.FavoriteRequest;
import com.library.engagement.dto.response.FavoriteResponse;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {

    FavoriteResponse add(CurrentUser currentUser, FavoriteRequest request);

    List<FavoriteResponse> getMyFavorites(CurrentUser currentUser);

    void remove(CurrentUser currentUser, UUID bookId);
}
