--liquibase formatted sql

--changeset higor:1
create table if not exists RELEASE_MANAGER.VERSION_STATUS (
 id int not null primary key auto_increment,
 name varchar(60) not null
);