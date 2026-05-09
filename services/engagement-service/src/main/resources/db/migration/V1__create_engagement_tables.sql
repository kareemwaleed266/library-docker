CREATE TABLE favorites (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_favorites_user_book
        UNIQUE (user_id, book_id)
);

CREATE TABLE reviews (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    rating INTEGER NOT NULL,
    comment TEXT,
    version BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT uk_reviews_user_book
        UNIQUE (user_id, book_id),

    CONSTRAINT chk_review_rating_range
        CHECK (rating BETWEEN 1 AND 5)
);

CREATE INDEX idx_favorites_user_id ON favorites(user_id);
CREATE INDEX idx_favorites_book_id ON favorites(book_id);

CREATE INDEX idx_reviews_user_id ON reviews(user_id);
CREATE INDEX idx_reviews_book_id ON reviews(book_id);
CREATE INDEX idx_reviews_rating ON reviews(rating);
