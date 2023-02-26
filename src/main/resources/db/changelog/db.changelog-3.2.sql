--liquibase formatted sql

--changeset dudkomikhail:1
alter table users add column verification_code uuid