--liquibase formatted sql

--changeset dudkomikhail:1
alter table users alter column password type varchar(78)