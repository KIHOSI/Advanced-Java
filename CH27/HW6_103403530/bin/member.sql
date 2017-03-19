CREATE DATABASE member;

USE member;

CREATE TABLE people
(
	MemberID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(30) NOT NULL,
    phone varchar(15) NOT NULL,
    e_mail varchar(50) NOT NULL,
    sex varchar(10) NOT NULL
)	;

INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (1,'小明','0921-912881','min@gmail.com','男');
INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (2,'大白','0987-878887','white@hotmail.com','男');
INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (3,'西南','0938-917223','johncena@yahoo.com','男');
INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (4,'莉莉','0920-384422','lily@cc,ncu.edu.tw','女');
INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (5,'莎莉','0922-298187','sally@gmail.com','女');
INSERT INTO people(MemberID,name,phone,e_mail,sex) VALUES (7,'小白','0914-882733','smallwhite@yahoo.com','女');
