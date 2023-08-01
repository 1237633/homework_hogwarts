package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> add(@RequestBody Faculty faculty) {
        if (faculty != null) {
            return ResponseEntity.ok(facultyService.addFaculty(faculty));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> get(@PathVariable int id) {
        if (id > 0) {
            return ResponseEntity.ok(facultyService.getFaculty(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> edit(@PathVariable int id, @RequestBody Faculty faculty) {
        if (id > 0 && faculty != null) {
            faculty.setId(id);
            return ResponseEntity.ok(facultyService.editFaculty(id, faculty));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> delete(@PathVariable int id) {
        if (id > 0) {
            return ResponseEntity.ok(facultyService.removeFaculty(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Collection<Faculty>> getByColor(@PathVariable String color) {
        if (color != null) {
            return ResponseEntity.ok(facultyService.getByColor(color));
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
