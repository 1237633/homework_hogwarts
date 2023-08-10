package ru.hogwarts.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyRepo extends JpaRepository<Faculty, Integer> {
    List<Faculty> findByColorLike(String color);

    List<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);
}
