drop table if exists users;

create table users(id smallint primary key identity, username varchar(255), phoneNumber varchar(15));

drop table if exists notification;

create table notification(id smallint primary key identity, message varchar(255), user_id smallint);

alter table notification add constraint user_id_fk foreign key (user_id) references users (id);