create schema auth;


create table auth.users (
id varchar(100),
username varchar(100),
password varchar(100),
email varchar(100),
is_active boolean,
token uuid
);

create sequence auth.users_id_seq;

alter table auth.users alter column id set default nextval('auth.users_id_seq');


select * from auth.users;
