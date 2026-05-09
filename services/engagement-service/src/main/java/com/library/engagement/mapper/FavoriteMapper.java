package com.library.engagement.mapper;

import com.library.engagement.dto.response.FavoriteResponse;
import com.library.engagement.entity.Favorite;
import org.springframework.stereotype.Component;

@Component
public class FavoriteMapper {

    public FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getUserId(),
                favorite.getBookId(),
                favorite.getCreatedAt()
        );
    }
}
