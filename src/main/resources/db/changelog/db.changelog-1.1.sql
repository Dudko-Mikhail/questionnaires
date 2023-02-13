--liquibase formatted sql

--changeset dudkomikhail:1
alter table fields add column if not exists options varchar(128)[];
--rollback alter table fields drop column options

--changeset dudkomikhail:2
alter table fields add unique (user_id, "order")