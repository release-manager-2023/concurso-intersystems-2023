--liquibase formatted sql

--changeset higor:1
create table if not exists RELEASE_MANAGER.VERSION_STATUS (
 id serial,
 name varchar(60) not null
);

--changeset higor:2
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Internal');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Canary');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Revoked');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('General Availability');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Deprecated');
