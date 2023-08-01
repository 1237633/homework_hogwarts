package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        if (student != null && student.getAge() > 6) {
            return ResponseEntity.ok(studentService.addStudent(student));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> get(@PathVariable int id) {
        if (id > 0) {
            return ResponseEntity.ok(studentService.getStudent(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> edit(@PathVariable int id, @RequestBody Student student) {
        if (id > 0 && student != null) {
            student.setId(id);
            return ResponseEntity.ok(studentService.editStudent(id, student));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable int id) {
        if (id > 0) {
            return ResponseEntity.ok(studentService.removeStudent(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("age/{age}")
    public ResponseEntity<Collection<Student>> getByAge(@PathVariable int age) {
        if (age > 6) {
            return ResponseEntity.ok(studentService.getByAge(age));
        }
        return ResponseEntity.badRequest().build();
    }
}
