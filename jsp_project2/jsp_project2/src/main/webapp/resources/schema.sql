create database jspdb;

create user 'jspuser'@'localhost' identified by 'mysql' ;

grant all privileges on javadb.* to 'jspuser'@'localhost' with grant option;

flush privileges;


use jspdb;

-- 2023-05-30
create table member(
id varchar(100) not null,
password varchar(100) not null,
email varchar(100) not null,
age int,
reg_date datetime default now(),
last_login datetime default null,
primary key(id));

-- 2023-05-31
create table board(
bno int not null auto_increment,
title varchar(100) not null,
writer varchar(100) not null,
content text,
count int not null default 0,
reg_date datetime default now(),
image_file text,
primary key(bno));

-- 2023-05-31
create table comment(
cno int not null auto_increment,
bno int default 0,
writer varchar(100) default "unkown",
content varchar(1000),
reg_date datetime default now(),
primary key(cno));