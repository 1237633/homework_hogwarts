package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.FacultyRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {
final
FacultyRepo facultyRepo;

    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepo.save(faculty);
    }

    public Faculty getFaculty(int id) {
        return facultyRepo.findById(id).orElseThrow(() -> new NoObjectInRepoException("No faculty found"));
    }

    public void removeFaculty(int id) {
        facultyRepo.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepo.save(faculty);
    }

    public Collection<Faculty> getByColor(String color) {
        return facultyRepo.findByColorLike(color);
    }
}
