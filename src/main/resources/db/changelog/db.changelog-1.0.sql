--liquibase formatted sql

--changeset dudkomikhail:1
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(64) NOT NULL UNIQUE,
    phone_number VARCHAR(32),
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    password VARCHAR(64) NOT NULL
);
--rollback DROP TABLE users

--changeset dudkomikhail:2
CREATE TABLE IF NOT EXISTS fields (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES users(id) NOT NULL,
    label VARCHAR(128) NOT NULL,
    "order" INT NOT NULL,
    type VARCHAR(16) NOT NULL,
    is_required bool NOT NULL,
    is_active bool NOT NULL
);
--rollback DROP TABLE fields