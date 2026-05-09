INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('LIBRARIAN'),
    ('USER')
ON CONFLICT (name) DO NOTHING;