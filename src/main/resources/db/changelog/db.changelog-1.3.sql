--liquibase formatted sql

--changeset dudkomikhail:1
alter table users add column if not exists is_activated bool not null default false
--rollback alter table users drop column if exists is_activated
