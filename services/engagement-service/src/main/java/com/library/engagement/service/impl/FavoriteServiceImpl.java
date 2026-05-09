package com.library.engagement.service.impl;

import com.library.common.exception.BadRequestException;
import com.library.common.exception.NotFoundException;
import com.library.common.security.CurrentUser;
import com.library.engagement.dto.request.FavoriteRequest;
import com.library.engagement.dto.response.FavoriteResponse;
import com.library.engagement.entity.Favorite;
import com.library.engagement.mapper.FavoriteMapper;
import com.library.engagement.repository.FavoriteRepository;
import com.library.engagement.service.FavoriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;

    public FavoriteServiceImpl(
            FavoriteRepository favoriteRepository,
            FavoriteMapper favoriteMapper
    ) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public FavoriteResponse add(CurrentUser currentUser, FavoriteRequest request) {
        if (favoriteRepository.existsByUserIdAndBookId(currentUser.id(), request.bookId())) {
            throw new BadRequestException("Book already exists in favorites");
        }

        Favorite favorite = new Favorite(
                currentUser.id(),
                request.bookId()
        );

        return favoriteMapper.toResponse(favoriteRepository.save(favorite));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteResponse> getMyFavorites(CurrentUser currentUser) {
        return favoriteRepository.findByUserIdOrderByCreatedAtDesc(currentUser.id())
                .stream()
                .map(favoriteMapper::toResponse)
                .toList();
    }

    @Override
    public void remove(CurrentUser currentUser, UUID bookId) {
        Favorite favorite = favoriteRepository.findByUserIdAndBookId(
                        currentUser.id(),
                        bookId
                )
                .orElseThrow(() -> new NotFoundException("Favorite not found"));

        favoriteRepository.delete(favorite);
    }
}
