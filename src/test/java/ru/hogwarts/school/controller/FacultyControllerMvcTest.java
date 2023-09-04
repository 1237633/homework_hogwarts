package ru.hogwarts.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.AvatarRepo;
import ru.hogwarts.school.repo.FacultyRepo;
import ru.hogwarts.school.repo.StudentRepo;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class FacultyControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepo facultyRepo;

    @SpyBean
    private FacultyService facultyService;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private StudentService studentService;


    @InjectMocks
    private FacultyController facultyController;

    private static String url = "/faculty/";

    private static String color;
    private static int id;
    private static String name;
    private static JSONObject facultyObj;
    private static Faculty faculty;

    @BeforeAll
    static void setUp() throws Exception {
        color = "Yellow";
        name = "Test";
        id = 1;

        facultyObj = new JSONObject();
        facultyObj.put("name", name);
        facultyObj.put("color", color);

        faculty = new Faculty();
        faculty.setColor(color);
        faculty.setName(name);

    }

    @Test
    void addFaculty() throws Exception {

        when(facultyRepo.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepo.findById(any(int.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .content(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(name));

    }

    @Test
    void getFaculty() throws Exception {
        when(facultyRepo.findById(any(int.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(name));

        System.err.println(mockMvc.perform(MockMvcRequestBuilders
                .get(url + id)).andReturn().getResponse().getContentAsString());
    }

    @Test
    void editFaculty() throws Exception {
        when(facultyRepo.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepo.findById(any(int.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(url + id)
                        .content(facultyObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value(color))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url + id))
                .andExpect(status().isOk())
                .andExpect(result -> verify(facultyService, times(1)).removeFaculty(id))
                .andExpect(result -> verify(facultyRepo, times(1)).deleteById(id));
    }

    @Test
    void getByColor() throws Exception {
        when(facultyRepo.findByColorContainingIgnoreCase(anyString())).thenReturn(List.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "color/" + color))
                .andExpect(status().isOk())
                .andExpect(result -> verify(facultyService, times(1)).getByColor(color))
                .andExpect(result -> verify(facultyRepo, times(1)).findByColorContainingIgnoreCase(color))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..color").value(color));
    }

    @Test
    void getByName() throws Exception {
        when(facultyRepo.findByColorIgnoreCaseOrNameIgnoreCase(nullable(String.class), anyString())).thenReturn(List.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "?name=" + name))
                .andExpect(status().isOk())
                .andExpect(result -> verify(facultyService, times(1)).getByColorOrName(null, name))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..name").value(name));
    }

    @Test
    void getStudents() throws Exception {
        when(facultyRepo.findById(anyInt())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get(url + id + "/students"))
                .andExpect(status().isOk())
                .andExpect(result -> verify(facultyService, times(1)).getStudents(id))
                .andExpect(result -> verify(facultyService, times(1)).getFaculty(id))
                .andExpect(result -> verify(facultyRepo, times(1)).findById(id))
                .andExpect(jsonPath("$").doesNotExist());


    }
}
