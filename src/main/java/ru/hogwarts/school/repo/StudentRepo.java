package ru.hogwarts.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findByAge(Integer age);
}
