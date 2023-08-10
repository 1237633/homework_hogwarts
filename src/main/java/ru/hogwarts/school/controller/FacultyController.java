package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
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
        if (id >= 0) {
            return ResponseEntity.ok(facultyService.getFaculty(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> edit(@PathVariable int id, @RequestBody Faculty faculty) {
        if (id >= 0 && faculty != null) {
            faculty.setId(id);
            return ResponseEntity.ok(facultyService.editFaculty(faculty));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        try {
            facultyService.removeFaculty(id);
        } catch (RuntimeException e) {
            throw new NoObjectInRepoException(e);
        }
    }

    @GetMapping("color/{color}")
    public ResponseEntity<Collection<Faculty>> getByColor(@PathVariable String color) {
        if (color != null) {
            return ResponseEntity.ok(facultyService.getByColor(color));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getByNameOrColor(@RequestParam(required = false) String name, @RequestParam(required = false) String color) {
        if(color != null && name !=null){  //В задании сказано что поиск должен идти по имени ИЛИ цвету, и в запросе передается одна строка
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.getByColorOrName(color, name));
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getStudents(@PathVariable int id) {
        if (id >= 0) {
            return ResponseEntity.ok(facultyService.getStudents(id));
        }
        return ResponseEntity.notFound().build();
    }
}
