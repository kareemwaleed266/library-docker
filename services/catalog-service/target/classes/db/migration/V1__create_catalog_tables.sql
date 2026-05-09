CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE books (
    id UUID PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(160) NOT NULL,
    isbn VARCHAR(30) NOT NULL UNIQUE,
    description TEXT,
    cover_image_url VARCHAR(1000),

    category_id UUID NOT NULL,

    total_copies INTEGER NOT NULL,
    available_copies INTEGER NOT NULL,

    published_year INTEGER,

    version BIGINT NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_books_category
        FOREIGN KEY (category_id)
        REFERENCES categories(id),

    CONSTRAINT chk_total_copies_non_negative
        CHECK (total_copies >= 0),

    CONSTRAINT chk_available_copies_non_negative
        CHECK (available_copies >= 0),

    CONSTRAINT chk_available_less_than_total
        CHECK (available_copies <= total_copies)
);

CREATE INDEX idx_books_title
    ON books(title);

CREATE INDEX idx_books_author
    ON books(author);

CREATE INDEX idx_books_category
    ON books(category_id);

CREATE INDEX idx_books_available
    ON books(available_copies);
