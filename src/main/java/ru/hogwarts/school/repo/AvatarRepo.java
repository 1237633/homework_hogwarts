package ru.hogwarts.school.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepo extends JpaRepository<Avatar, Integer> {
    Optional<Avatar> findByStudentId(int id);
}
