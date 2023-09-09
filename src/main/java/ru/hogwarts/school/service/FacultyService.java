package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private FacultyRepo facultyRepo;
    private Logger logger;
    private String currMethod;

    public FacultyService(FacultyRepo facultyRepo) {
        this.facultyRepo = facultyRepo;
        this.logger = LoggerFactory.getLogger(FacultyService.class);
    }

    public Faculty addFaculty(Faculty faculty) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return facultyRepo.save(faculty);
    }

    public Faculty getFaculty(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return facultyRepo.findById(id).orElseThrow(() -> new NoObjectInRepoException("No faculty found"));
    }

    public void removeFaculty(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        facultyRepo.deleteById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return facultyRepo.save(faculty);
    }

    public Collection<Faculty> getByColor(String color) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return facultyRepo.findByColorContainingIgnoreCase(color);
    }

    public Collection<Faculty> getByColorOrName(String color, String name) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return facultyRepo.findByColorIgnoreCaseOrNameIgnoreCase(color, name);
    }

    public Collection<Student> getStudents(int id) {
        currMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.debug("Executing method {}", currMethod);
        return getFaculty(id).getStudents();
    }
}
