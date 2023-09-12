package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.StudentRepo;


import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    final
    private StudentRepo studentRepo;
    private final Logger logger;
    private String currMethod;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
        this.logger = LoggerFactory.getLogger(StudentService.class);
    }

    public Student addStudent(Student student) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.save(student);
    }

    public Student getStudent(int id){
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.findById(id).orElseThrow(() -> new NoObjectInRepoException("No student found"));
    }

    public void removeStudent(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        studentRepo.deleteById(id);
    }

    public Student editStudent(Student student) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.save(student);
    }

    public Collection<Student> getByAge(int age) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.findByAge(age);
    }

    public Collection<Student> getByAge(int minAge, int maxAge) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getFaculty(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return getStudent(id).getFaculty();
    }

    public Integer getCount() {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.getStudentsCount();
    }

    public double getAvgAge() {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return studentRepo.getAvgAge();
    }

    public List<Student> getLastFive() {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
       return studentRepo.getLastFive();
    }

    public List<Student> sort() {
        List<Student> students = studentRepo.findAll();
        List<Student> sorted = students.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
        return sorted;
    }

    public double getAvgAgeStream() {
        List<Student> students = studentRepo.findAll();
        return students.stream().mapToInt(Student::getAge).average().getAsDouble();
    }
}

