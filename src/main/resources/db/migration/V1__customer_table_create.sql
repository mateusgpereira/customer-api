CREATE TABLE customer
(
    id BIGSERIAL PRIMARY KEY,
    first_name  TEXT NOT NULL,
    middle_name TEXT,
    last_name   TEXT NOT NULL,
    suffix      TEXT,
    email       TEXT,
    phone       TEXT
);
ALTER SEQUENCE customer_id_seq RESTART 1000;