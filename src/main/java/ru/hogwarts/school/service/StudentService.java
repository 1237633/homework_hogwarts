package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.StudentRepo;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    final
    StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }

    public Student getStudent(int id){
        return studentRepo.findById(id).orElseThrow(() -> new NoObjectInRepoException("No student found"));
    }

    public void removeStudent(int id) {
        studentRepo.deleteById(id);
    }

    public Student editStudent(Student student) {
        return studentRepo.save(student);
    }

    public Collection<Student> getByAge(int age) {
        return studentRepo.findByAge(age);
    }

    public Collection<Student> getByAge(int minAge, int maxAge) {
        return studentRepo.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFaculty(int id) {
        return studentRepo.findById(id).orElseThrow(() -> new NoObjectInRepoException("No student found")).getFaculty();
    }
}

