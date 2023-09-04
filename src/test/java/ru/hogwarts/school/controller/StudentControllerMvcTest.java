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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StudentControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepo studentRepo;

    @SpyBean
    private StudentService studentService;

    @MockBean
    private AvatarRepo avatarRepo;

    @MockBean
    private AvatarService avatarService;

    @MockBean
    private FacultyService facultyService;

    @MockBean
    private FacultyRepo facultyRepo;


    @InjectMocks
    private StudentController studentController;

    private static String url = "/student/";

    private static int age;
    private static int id;
    private static String name;
    private static JSONObject studentObj;
    private static Student student;

    @BeforeAll
    static void setUp() throws Exception {
        age = 12;
        name = "Test";
        id = 1;

        studentObj = new JSONObject();
        studentObj.put("name", name);
        studentObj.put("age", age);

        student = new Student();
        student.setAge(age);
        student.setName(name);

    }

    @Test
    void addStudent() throws Exception {

        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(studentRepo.findById(any(int.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(url)
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));

    }

    @Test
    void getStudent() throws Exception {
        when(studentRepo.findById(any(int.class))).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));

        System.err.println(mockMvc.perform(MockMvcRequestBuilders
                .get(url + id)).andReturn().getResponse().getContentAsString());
    }

    @Test
    void editStudent() throws Exception {
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(studentRepo.findById(any(int.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(url + id)
                        .content(studentObj.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(age))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url + id))
                .andExpect(status().isOk())
                .andExpect(result -> verify(studentService, times(1)).removeStudent(id))
                .andExpect(result -> verify(studentRepo, times(1)).deleteById(id));
    }

    @Test
    void getByAge() throws Exception {
        when(studentRepo.findByAge(any(int.class))).thenReturn(List.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "age/" + age))
                .andExpect(status().isOk())
                .andExpect(result -> verify(studentService, times(1)).getByAge(age))
                .andExpect(result -> verify(studentRepo, times(1)).findByAge(age))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..age").value(age));
    }

    @Test
    void getByAges() throws Exception {
        when(studentRepo.findByAgeBetween(any(int.class), any(int.class))).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(url + "age/" + "?min=" + age + "&max=" + (age + 2)))
                .andExpect(status().isOk())
                .andExpect(result -> verify(studentService, times(1)).getByAge(age, age + 2))
                .andExpect(result -> verify(studentRepo, times(1)).findByAgeBetween(age, age + 2))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$..age").value(age));
    }

    @Test
    void getFaculty() throws Exception{
        when(studentRepo.findById(anyInt())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get(url + id + "/faculty"))
                .andExpect(status().isOk())
                .andExpect(result -> verify(studentService, times(1)).getFaculty(id))
                .andExpect(result -> verify(studentService, times(1)).getStudent(id))
                .andExpect(result -> verify(studentRepo, times(1)).findById(id))
                .andExpect(jsonPath("$").doesNotExist());
    }
}
