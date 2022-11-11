
-- drop table users;

create table users (
    id int not null generated always as identity, 
    name varchar(100), 
    primary key (id) 
);



