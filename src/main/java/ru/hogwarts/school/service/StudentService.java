package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private HashMap<Integer, Student> students;
    private int idCounter;

    public StudentService() {
        this.students = new HashMap<>();
        this.idCounter = 0;
    }

    public Student addStudent(Student student) {
        idCounter++;
        student.setId(idCounter);
        return students.put(idCounter, student);
    }

    public Student getStudent(int id) {
        return students.get(id);

    }

    public Student removeStudent(int id) {
        return students.remove(id);
    }

    public Student editStudent(int id, Student student) {
        return students.put(id, student);
    }

    public Collection<Student> getByAge(int age) {
        return students.values().stream().filter(student -> student.getAge() == age).collect(Collectors.toSet());
    }
}
