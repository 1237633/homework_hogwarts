package ru.hogwarts.school.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    FacultyController facultyController;

    @Autowired
    TestRestTemplate testRestTemplate;
    private static int id;
    private static String url = "/faculty/";

    @Test
    @Order(1)
    void contextLoads() throws Exception {
        Assertions.assertNotNull(facultyController);
    }

    @Test
    @Order(3)
    void getStudent() throws Exception {
        Assertions.assertNotNull(this.testRestTemplate.getForObject(url, Faculty.class));
    }

    @Test
    @Order(2)
    void addStudent() throws Exception {
        Faculty test = new Faculty();
        test.setName("Java");
        test.setColor("Black");
        id = this.testRestTemplate.postForObject(url, test, Faculty.class).getId();
        url = url + id;
        System.out.println("id = " + id);
        System.out.println("url = " + url);
        Assertions.assertEquals(test, facultyController.get(id).getBody());
    }

    @Test
    @Order(4)
    void editStudent() throws Exception {
        Faculty test = new Faculty();
        test.setName("C++");
        test.setColor("Transparent");
        this.testRestTemplate.put(url, test);
        Assertions.assertEquals(test, this.testRestTemplate.getForObject(url, Faculty.class));
    }

    @Test
    @Order(5)
    void deleteStudent() throws Exception {
        System.out.println(id);
        System.out.println(url);
        this.testRestTemplate.delete(url);
        Assertions.assertThrows(NoObjectInRepoException.class, () -> facultyController.get(id));
    }

    @Test
    void getByColor() {
        Assertions.assertInstanceOf(Collection.class, this.testRestTemplate.getForObject("/faculty/color/Red", Collection.class));
    }

    @Test
    void getByColorOrName() {
        Assertions.assertInstanceOf(Collection.class, this.testRestTemplate.getForObject("/faculty/?color=Red", Collection.class));
    }

    @Test
    void getByName() {
        Assertions.assertInstanceOf(Collection.class, this.testRestTemplate.getForObject("/faculty/?name=Griff", Collection.class));
    }

    @Test
    void getStudents() {
        Faculty test = new Faculty();
        test.setColor("Black");
        test.setName("Test");
        int testId = this.testRestTemplate.postForObject("/faculty", test, Faculty.class).getId();
        Assertions.assertNotNull(this.testRestTemplate.getForObject("/faculty/" + testId + "/students", Collection.class));
        facultyController.delete(testId);
    }

}