--liquibase formatted sql

--changeset dudkomikhail:1
create table if not exists questionnaires (
    id bigserial primary key,
    user_id bigint not null references users(id) on delete cascade,
    title varchar(255) not null,
    description text,
    is_active bool not null
);
--rollback drop table questionnaires

--changeset dudkomikhail:2
alter table fields drop column user_id;
alter table fields add column questionnaire_id bigint references questionnaires(id) on delete cascade;

--changeset dudkomikhail:3
alter table responses drop column user_id;
alter table responses add column questionnaire_id bigint references questionnaires(id) on delete cascade;
