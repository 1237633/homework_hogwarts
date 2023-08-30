package ru.hogwarts.school.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repo.FacultyRepo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    FacultyRepo facultyRepoMock;
    @InjectMocks
    FacultyService facultyService;

    public Faculty testFaculty = new Faculty();

    @BeforeEach
    void setUp() {
        testFaculty.setId(1);
        testFaculty.setColor("Green");
        testFaculty.setName("Griff");
    }

    @Test
    void addReturnsFaculty() {
        when(facultyRepoMock.save(testFaculty)).thenReturn(testFaculty);
        Assertions.assertInstanceOf(Faculty.class, facultyService.addFaculty(testFaculty));
    }

    @Test
    void addReturnsCorrectId() {
        when(facultyRepoMock.save(testFaculty)).thenReturn(testFaculty);
        Assertions.assertEquals(1, facultyService.addFaculty(testFaculty).getId());
    }

    @Test
    void getReturnsCorrectObject() {
        when(facultyRepoMock.save(testFaculty)).thenReturn(testFaculty);
        facultyService.addFaculty(testFaculty);
        when(facultyRepoMock.findById(1)).thenReturn(Optional.of(testFaculty));
        Assertions.assertEquals(testFaculty, facultyService.getFaculty(1));
    }

    @Test
    void getReturnsExceptionIfNotFound() {
        when(facultyRepoMock.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoObjectInRepoException.class, () -> facultyService.getFaculty(1));
    }

    @Test
    void editReturnsNewObj() {
        when(facultyRepoMock.save(testFaculty)).thenReturn(testFaculty);
        when(facultyRepoMock.findById(1)).thenReturn(Optional.of(testFaculty));
        Assertions.assertEquals(facultyService.getFaculty(1), facultyService.editFaculty(testFaculty));
    }

    @Test
    void getByColor() {
        when(facultyRepoMock.findByColorContainingIgnoreCase("Green")).thenReturn(List.of(testFaculty));
        Assertions.assertTrue(facultyService.getByColor("Green").stream().allMatch(e -> e.getColor().equals("Green")));
    }
}