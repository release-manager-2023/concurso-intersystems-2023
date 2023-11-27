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

--changeset higor:5
create table if not exists RELEASE_MANAGER.PRODUCT (
  id bigint not null primary key auto_increment,
  name varchar(60) not null,
  major_version int,
  minor_version int,
  patch_version int,
  revision_version int
);

--changeset higor:6
create table if not exists RELEASE_MANAGER.CUSTOMER (
  id bigint not null primary key auto_increment,
  name varchar(80) not null,
  custom_customer_id varchar(80),
  current_major_version int,
  current_minor_version int,
  current_patch_version int,
  current_revision_version int
);

--changeset higor:7
create table if not exists RELEASE_MANAGER.VERSION (
  id bigint not null primary key auto_increment,
  product_id bigint references RELEASE_MANAGER.PRODUCT(id) not null,
  version_status_id bigint references RELEASE_MANAGER.VERSION_STATUS(id) not null,
  artifact_location varchar(255),
  username varchar(80),
  version_created_timestamp timestamp default 'now',
  major_version int,
  minor_version int,
  patch_version int,
  revision_version int
);
