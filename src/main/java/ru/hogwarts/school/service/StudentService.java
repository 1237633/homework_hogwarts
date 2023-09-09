package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.StudentRepo;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Service
public class StudentService {
    final
    private StudentRepo studentRepo;
    private Logger logger;
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
}

