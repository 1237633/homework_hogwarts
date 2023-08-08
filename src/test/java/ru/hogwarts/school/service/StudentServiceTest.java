package ru.hogwarts.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.StudentRepo;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    StudentRepo studentRepoMock;
    @InjectMocks
    StudentService studentService;

    public Student testStudent = new Student();

    @BeforeEach
    void setUp() {
        testStudent.setId(1);
        testStudent.setAge(12);
        testStudent.setName("Griff");
    }

    @Test
    void addReturnsStudent() {
        when(studentRepoMock.save(testStudent)).thenReturn(testStudent);
        Assertions.assertInstanceOf(Student.class, studentService.addStudent(testStudent));
    }

    @Test
    void addReturnsCorrectId() {
        when(studentRepoMock.save(testStudent)).thenReturn(testStudent);
        Assertions.assertEquals(1, studentService.addStudent(testStudent).getId());
    }

    @Test
    void getReturnsCorrectObject() {
        when(studentRepoMock.save(testStudent)).thenReturn(testStudent);
        studentService.addStudent(testStudent);
        when(studentRepoMock.findById(1)).thenReturn(Optional.of(testStudent));
        Assertions.assertEquals(testStudent, studentService.getStudent(1));
    }

    @Test
    void getReturnsExceptionIfNotFound() {
        when(studentRepoMock.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoObjectInRepoException.class, () -> studentService.getStudent(1));
    }

    @Test
    void editReturnsNewObj() {
        when(studentRepoMock.save(testStudent)).thenReturn(testStudent);
        when(studentRepoMock.findById(1)).thenReturn(Optional.of(testStudent));
        Assertions.assertEquals(studentService.getStudent(1), studentService.editStudent(testStudent));
    }

    @Test
    void getByColor() {
        when(studentRepoMock.findByAge(12)).thenReturn(List.of(testStudent));
        Assertions.assertTrue(studentService.getByAge(12).stream().allMatch(e -> e.getAge() == 12));
    }
}