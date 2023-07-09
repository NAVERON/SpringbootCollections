
-- drop table IS exists users;

create table users (
    id int not null generated always as identity comment 'db id',
    account_id char(64) not null default '' comment 'id from other account source',
    name varchar(100) not null comment 'user account name',
    nick_name varchar(100) default 'default' comment 'user nick name',
    password CHAR(50) not null default '***' comment 'user pwd5',
    create_time datetime not null default current_timestamp comment 'create time',
    update_time datetime not null default current_timestamp on update current_timestamp comment 'update time',
    primary key (id) 
);



