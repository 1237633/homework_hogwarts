package ru.hogwarts.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    List<Student> findByAge(Integer age);

    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    @Query(value = "SELECT count(*) from Student", nativeQuery = true)
    Integer getStudentsCount();

    @Query(value = "SELECT AVG(age) from Student", nativeQuery = true)
    float getAvgAge();

    @Query(value = "SELECT * from Student ORDER BY id desc nulls last LIMIT 5" , nativeQuery = true)
    List<Student> getLastFive();
}
