CREATE TABLE borrow_transactions (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    borrow_date TIMESTAMP WITH TIME ZONE,
    due_date TIMESTAMP WITH TIME ZONE,
    returned_date TIMESTAMP WITH TIME ZONE,
    fine_amount NUMERIC(10, 2) NOT NULL DEFAULT 0,
    approved_by UUID,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE reservations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    book_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    queue_order INTEGER NOT NULL,
    reservation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    expiry_date TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_borrow_user_id ON borrow_transactions(user_id);
CREATE INDEX idx_borrow_book_id ON borrow_transactions(book_id);
CREATE INDEX idx_borrow_status ON borrow_transactions(status);
CREATE INDEX idx_borrow_due_date ON borrow_transactions(due_date);

CREATE INDEX idx_reservation_user_id ON reservations(user_id);
CREATE INDEX idx_reservation_book_id ON reservations(book_id);
CREATE INDEX idx_reservation_status ON reservations(status);
CREATE INDEX idx_reservation_queue ON reservations(book_id, status, queue_order);
