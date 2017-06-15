drop table if exists users;

create table users(
    username varchar(256) not null primary key,
    password varchar(256) not null,
    enabled boolean not null
);

drop table if exists authorities;

create table authorities (
    username varchar(256) not null,
    authority varchar(256) not null,
    constraint fk_authorities_users
    foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

drop table if exists groups;

create table groups (
    id bigint NOT NULL AUTO_INCREMENT primary key,
    group_name varchar(50) not null
);

drop table if exists group_authorities;

create table group_authorities (
    group_id bigint not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key(group_id) references groups(id)
);

drop table if exists group_members;

create table group_members (
    id bigint NOT NULL AUTO_INCREMENT primary key,
    username varchar(50) not null,
    group_id bigint not null,
    constraint fk_group_members_group foreign key(group_id) references groups(id)
);
