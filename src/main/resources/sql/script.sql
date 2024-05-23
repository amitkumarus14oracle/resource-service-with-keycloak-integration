create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);
insert into users values ('amit', 'amit', true);
insert into authorities values ('amit', 'write');

create table employee(
id int not null auto_increment,
email varchar(200) not null,
password varchar(200) not null,
role varchar(45) not null,
primary key(id)
);

insert into employee(email, password, role) values ('amitkumarus14jan@gmail.com', '12345', 'admin');