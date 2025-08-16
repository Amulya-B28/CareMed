create database if not exists college;
create database college;

drop database if exists company;

show databases;

use college;

create table student(
id int primary key,
name varchar(50),
age int not null
);

insert into student values(1,"AMY",22);
insert into student values(2,"V",21);

select * from student;

drop table student;

create table student(
roll int primary key,
name varchar(50)
);

insert into student (roll,name) values
(1,"Amy"),
(2,"V");
insert into student (roll,name) values
(3,"Yuv"),
(4,"Zen");

select * from student;
select roll from student;
select name from student;

drop table temp1;
create table course(
id int primary key,
course varchar(20));
truncate table course;
insert into course values(2,"Science"),(5,"IT"),(4,"English"),(6,"Math");
select * from course;

/*INNER JOIN*/
select * from student
inner join course
on student.roll=course.id;

select * from student as s
inner join course as c
on s.roll=c.id;

/*LEFT JOIN*/
select * 
from student as s
left join course as c
on s.roll=c.id;

/*RIGHT JOIN*/
select * 
from student as s
right join course as c
on s.roll=c.id;
/*why to do left n right when u can print the tables only?*/

/*FULL JOIN*/
select * 
from student as s
left join course as c
on s.roll=c.id;
union
select *
from student as s
right join course as c
on s.roll=c.id;

/*LEFT EXCLUSIVE JOIN*/
select * 
from student as s
left join course as c
on s.roll=c.id
where c.id is null;

/*RIGHT EXCLUSIVE JOIN*/
select * 
from student as s
right join course as c
on s.roll=c.id
where s.roll is null;

/*SELF JOIN*/

create table employ(
id int primary key,
name varchar(50),
mng_id int
);
insert into employ(id,name,mng_id) values
(101,"adam",103),(102,"bob",104),(103,"casey",null),(104,"donald",103);
select * from employ;

select * 
from employ as a
join employ as b
on a.id=b.mng_id;

select a.name, b.name 
from employ as a
join employ as b
on a.id=b.mng_id;

select a.name as mng_name, b.name 
from employ as a
join employ as b
on a.id=b.mng_id;


select name from employ
union
select name from employ;

select name from employ
union all				/*ALLOWS DUPLICATES*/
select name from employ;


