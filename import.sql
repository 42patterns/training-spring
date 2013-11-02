create database translations;

create table words (
	id int not null auto_increment primary key,
	polish_word varchar(100),
	english_word varchar(100)
);
