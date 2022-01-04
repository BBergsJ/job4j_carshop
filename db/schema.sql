create database carshop;

create table if not exists users (
                                     id serial primary key,
                                     name varchar(200),
                                     email varchar(200)
);

create table if not exists brands (
                                      id serial primary key,
                                      name varchar(200)
);

create table if not exists cars (
                                    id serial primary key,
                                    model varchar(200),
                                    year varchar(10),
                                    brand_id int not null references brands(id)
);

create table if not exists posts (
                                     id serial primary key,
                                     description varchar(200),
                                     created timestamp,
                                     sold boolean not null default false,
                                     user_id int references users(id),
                                     car_id int references cars(id)
);

create table if not exists images (
                                      id serial primary key,
                                      name varchar(200),
                                      post_id int references posts(id)
);

create table if not exists CarType (
                                       id serial primary key,
                                       name varchar(200)
);

alter table posts add column type_id int references CarType(id);

alter table posts add column brand_id int references brands(id);

alter table users add column password varchar(200);