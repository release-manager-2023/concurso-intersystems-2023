--liquibase formatted sql

--changeset higor:1
create table if not exists RELEASE_MANAGER.VERSION_STATUS (
 id bigint not null primary key auto_increment,
 name varchar(60) not null
);

--changeset higor:2
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Internal');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Canary');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Revoked');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('General Availability');
insert into RELEASE_MANAGER.VERSION_STATUS (NAME) values ('Deprecated');

--changeset higor:3
create table if not exists RELEASE_MANAGER.STAKEHOLDER (
  id bigint not null primary key auto_increment,
  name varchar(60) not null,
  stakeholder_role varchar(60),
  email varchar(100)
);

--changeset higor:4
create table if not exists RELEASE_MANAGER.STAKEHOLDER_STATUSVERSION (
  stakeholder_id bigint not null references RELEASE_MANAGER.STAKEHOLDER(id),
  status_version_id bigint not null references RELEASE_MANAGER.VERSION_STATUS(id),
  primary key (stakeholder_id,status_version_id)
);