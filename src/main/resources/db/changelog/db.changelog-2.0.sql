--liquibase formatted sql

--changeset dudkomikhail:1
create table if not exists responses (
    id bigserial primary key,
    user_id bigint not null references users(id),
    answers json not null
)
--rollback drop table responses