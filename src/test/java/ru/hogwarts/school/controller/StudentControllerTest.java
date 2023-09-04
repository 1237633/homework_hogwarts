package ru.hogwarts.school.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import ru.hogwarts.school.exceptions.NoObjectInRepoException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;


import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    StudentController studentController;

    @Autowired
    TestRestTemplate testRestTemplate;
    private static int id;
    private static String url = "/student/";

    @Test
    @Order(1)
    void contextLoads() throws Exception {
        Assertions.assertNotNull(studentController);
    }

    @Test
    @Order(2)
    void getStudentHarry() throws Exception {
        Assertions.assertEquals("Harry", this.testRestTemplate.getForObject("/student/7", Student.class).getName());
    }

    @Test
    @Order(4)
    void getStudent() throws Exception {
        Assertions.assertNotNull(this.testRestTemplate.getForObject(url, Student.class));
    }

    @Test
    @Order(3)
    void addStudent() throws Exception {
        Student test = new Student();
        test.setName("Bolek");
        test.setAge(16);
        id = this.testRestTemplate.postForObject(url, test, Student.class).getId();
        url = url + id;
        System.out.println("id = " + id);
        System.out.println("url = " + url);
        Assertions.assertEquals(test, studentController.get(id).getBody());
    }

    @Test
    @Order(5)
    void editStudent() throws Exception {
        Student test = new Student();
        test.setName("Alkogolek");
        test.setAge(36);
        this.testRestTemplate.put(url, test);
        Assertions.assertEquals(test, this.testRestTemplate.getForObject(url, Student.class));
    }

    @Test
    @Order(6)
    void deleteStudent() throws Exception {
        System.out.println(id);
        System.out.println(url);
        this.testRestTemplate.delete(url);
        Assertions.assertThrows(NoObjectInRepoException.class, () -> studentController.get(id));
    }

    @Test
    void getByAge() {
        //По факту почему-то возвращается лист LinkedHashSetов, в виде которых студенты представлены в БД.
        Assertions.assertInstanceOf(Collection.class, this.testRestTemplate.getForObject("/student/age/15", Collection.class));
    }

    @Test
    void getByAges() {
        Assertions.assertInstanceOf(Collection.class, this.testRestTemplate.getForObject("/student/age?min=15&max=17", Collection.class));
    }

    @Test
    void getFaculty() {
        Student test = new Student();
        test.setAge(12);
        test.setName("Test");
        int testId = this.testRestTemplate.postForObject("/student/", test, Student.class).getId();
        //New student faculty is always == null
        Assertions.assertNull(this.testRestTemplate.getForObject("/student/" + testId, Student.class).getFaculty());
        studentController.delete(testId);
    }
}