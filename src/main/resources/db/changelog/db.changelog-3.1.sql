--liquibase formatted sql

--changeset dudkomikhail:1
insert into field_types (type)
values ('single line text'),
       ('multiline text'),
       ('radio button'),
       ('checkbox'),
       ('combobox'),
       ('date')