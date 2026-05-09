INSERT INTO categories (id, name, description, created_at, updated_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Programming', 'Books about programming and software development', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222222', 'Database', 'Books about database systems and SQL', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('33333333-3333-3333-3333-333333333333', 'Software Engineering', 'Books about software engineering principles and practices', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (name) DO NOTHING;

INSERT INTO books (
    id,
    title,
    author,
    isbn,
    description,
    cover_image_url,
    category_id,
    total_copies,
    available_copies,
    published_year,
    version,
    created_at,
    updated_at
)
VALUES
    (
        'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
        'Clean Code',
        'Robert C. Martin',
        '9780132350884',
        'A handbook of agile software craftsmanship',
        'https://res.cloudinary.com/demo/image/upload/sample.jpg',
        '11111111-1111-1111-1111-111111111111',
        5,
        5,
        2008,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb',
        'Database System Concepts',
        'Abraham Silberschatz',
        '9780073523323',
        'A classic textbook about database systems',
        'https://res.cloudinary.com/demo/image/upload/sample.jpg',
        '22222222-2222-2222-2222-222222222222',
        4,
        4,
        2010,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        'cccccccc-cccc-cccc-cccc-cccccccccccc',
        'Software Engineering',
        'Ian Sommerville',
        '9780137035151',
        'A textbook covering software engineering principles',
        'https://res.cloudinary.com/demo/image/upload/sample.jpg',
        '33333333-3333-3333-3333-333333333333',
        3,
        3,
        2011,
        0,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    )
ON CONFLICT (isbn) DO NOTHING;
