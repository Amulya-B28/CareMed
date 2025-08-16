create database coll;

use coll;

create table stu(
roll int primary key,
name varchar(50),
marks int not null,
grade varchar(1),
city varchar(20)
);

insert into stu (roll,name,marks,grade,city) values
(1,"ABC",78,"C","Pune"),
(2,"DEF",88,"B","MUMBAI"),
(3,"GHI",99,"A","BLORE"),
(4,"JKL",60,"D","MLORE"),
(5,"MNO",84,"B","DELHI");

select name,marks,grade from stu;
select distinct city from stu;
/*unique or non repeated values*/

/*CLAUSE 1 : WHERE*/
select * from stu where marks>80;
select * from stu where grade="B";

/*OPERATORS*/
select * from stu where marks>80 and city="blore"; /*NOT CASE SENSITIVE*/
/*OR (to check for one of the conditions to be true)*/
select * from stu where marks>70 or grade="b";
/*Between (selects for a given range)*/
select * from stu where marks between 70 and 90;
/*In (matches any value in the list)*/
select * from stu where city in ("delhi","blore");
/*NOT (to negate the given condition)*/
select * from stu where city not in ("delhi","blore");

/*CLAUSE 2 : LIMIT*/
select * from stu limit 3;
select * from stu where marks>70 limit 3;  /*Sets an upper limit on number of (tuples)rows to be returned*/

/*CLAUSE 3 : ORDER BY*/
select * from stu order by city asc;
select * from stu order by marks desc;
select * from stu order by marks desc limit 3;

/*FUNCTION*/
select max(marks) from stu;
select min(marks) from stu;
select count(marks) from stu;
select sum(marks) from stu;
select avg(marks) from stu;
select sum(grade) from stu; /*OUTPUT : 0 and not error*/

/*CLAUSE 4 : GROUP BY*/
select city from stu group by city;
select city from stu group by city order by city asc;

select city, count(roll) 
from stu 
group by city; /*usually used with an aggregate function*/

select city, avg(roll) 
from stu 
group by city
order by city asc;

/*CLAUSE 5 : HAVING*/
select city, count(roll) 
from stu 
group by city
having max(marks)>80; /*similar to where but used to test condition on a group only*/

/*General Order
SELECT column(s)
FROM table_name
WHERE condition
GROUP BY column(s)
HAVING condition
ORDER BY column(s) ASC;*/

select city 
from stu 
where grade="b"
group by city
having max(marks)>85;

/*UPDATE*/
update stu 
set grade="O"
where grade="A"; /*Error Code: 1175. You are using safe update mode and you tried to update a table without a WHERE that uses a KEY column*/

set sql_safe_updates=0;

select * from stu;

update stu 
set marks=marks+1;
select * from stu;

/*DELETE*/
delete from stu
where marks<60;


/*FOREIGN KEY*/
create table dept(
id int primary key,
name varchar(50)
);

create table teacher(
id int primary key,
name varchar(50),
dept_id int,
foreign key (dept_id) references dept(id)
on update cascade
on delete cascade
);
/*On Delete Cascade: it deletes the referencing rows in the child table when the referenced row is deleted in the parent table which has a primary key.
On Update Cascade: the referencing rows are updated in the child table when the referenced row is updated in the parent table which has a primary key.*/

insert into dept values(1,"Science"),(2,"IT"),(3,"English");
select * from dept;

insert into teacher values(101,"ABC",1),(102,"DEF",2),(103,"GHI",3),(104,"JKL",2);
select * from teacher;

update dept
set id=4
where id=2;

select * from dept;
select * from teacher;


/*ALTER*/
alter table stu
add column age int;
select * from stu;
UPDATE `coll`.`stu` SET `age` = '22' WHERE (`roll` = '1');
UPDATE `coll`.`stu` SET `age` = '21' WHERE (`roll` = '2');
UPDATE `coll`.`stu` SET `age` = '20' WHERE (`roll` = '3');
UPDATE `coll`.`stu` SET `age` = '21' WHERE (`roll` = '4');
UPDATE `coll`.`stu` SET `age` = '23' WHERE (`roll` = '5');

alter table stu add column hobbies int;
alter table stu drop column hobbies;

alter table stu rename to stud;
alter table stud rename to stu;

alter table stu change roll rollno int;

/*alter table stu modify cilumn age int not null default 19;*/
alter table stu modify age varchar(2);
alter table stu modify age int;

/*truncate table stu;---used to delete the data in table without deleting table*/




