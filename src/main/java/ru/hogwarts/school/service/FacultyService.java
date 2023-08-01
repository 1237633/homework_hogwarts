package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private HashMap<Integer, Faculty> faculties;
    private int idCounter;

    public FacultyService() {
        this.faculties = new HashMap<>();
        this.idCounter = 0;
    }

    public Faculty addFaculty(Faculty faculty) {
        idCounter++;
        faculty.setId(idCounter);
        return faculties.put(idCounter, faculty);
    }

    public Faculty getFaculty(int id) {
        return faculties.get(id);

    }

    public Faculty removeFaculty(int id) {
        return faculties.remove(id);
    }

    public Faculty editFaculty(int id, Faculty faculty) {
        return faculties.put(id, faculty);
    }

    public Collection<Faculty> getByColor(String color) {
        return faculties.values().stream().filter(faculty -> faculty.getColor().equals(color)).collect(Collectors.toSet());
    }

}
