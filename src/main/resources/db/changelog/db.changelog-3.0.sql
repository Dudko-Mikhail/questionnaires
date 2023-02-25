--liquibase formatted sql

--changeset dudkomikhail:1
create table if not exists field_types (
    id serial primary key,
    type varchar(32) not null unique
);
--rollback drop table field_types

--changeset dudkomikhail:2
alter table fields
    drop column type;

--changeset dudkomikhail:3
alter table fields
    add column type_id int not null references field_types(id)