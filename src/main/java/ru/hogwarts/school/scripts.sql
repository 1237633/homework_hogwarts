SELECT * FROM student;

select *
from student
where age between 10 and 20;

select name
from student;

select *
from student where name like '%o%' or name like '%O%';

select *
from student
where age < id;

select *
from student
order by age;

--проверка последнего задания:

select s.*, f.name
from student as s, faculty as f
where f.color = 'Pink' and s.faculty_id = f.id;

