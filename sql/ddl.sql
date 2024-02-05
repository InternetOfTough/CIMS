drop table if exists member CASCADE;
create table member
(
 code bigint generated by default as identity,
 name varchar(255),
 primary key (code)
);

alter table member add id varchar(255) not null;
insert into member( id , name , passwd ) values ('haha', 'im', 'spring');



// MySQL
1. create database hjproject;
2. DROP TABLE member;


CREATE TABLE member
(
	CODE BIGINT PRIMARY KEY auto_increment NULL,
	NAME VARCHAR(255) NOT NULL,
	id VARCHAR(255) NOT NULL,
	passwd VARCHAR(255) NOT NULL
);

3. ALTER TABLE member ADD sex VARCHAR(100) NULL;
   ALTER TABLE member ADD email VARCHAR(100) NULL;
   ALTER TABLE member ADD emaddress VARCHAR(100) NULL;

4. ALTER TABLE `member` ADD COLUMN `Register_data` DATE NULL AFTER `emaddress`;

5. ALTER TABLE `member` CHANGE COLUMN `Register_data` `Register_data` DATE NOT NULL DEFAULT now() AFTER `emaddress`;