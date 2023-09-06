SELECT s.name, s.age, f.name
FROM student AS s INNER JOIN faculty AS f ON s.faculty_id = f.id;

SELECT s.name, s.age, a.file_path
FROM student AS s RIGHT JOIN avatar AS a ON s.id = a.student_id;