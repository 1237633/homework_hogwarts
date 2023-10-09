package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repo.StudentRepo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MiscService {
    private final int useless = 11111;

    private final StudentRepo studentRepo;

    public MiscService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public int lesson45Step4() {
        long start = System.currentTimeMillis();
        long end;
        int sum = Stream
                .iterate(1, a -> a + 1)
                .limit(1_000_000)
                .mapToInt(e -> e)
                .sum();
        end = System.currentTimeMillis();
        System.err.println(end - start);
        return sum;
    }

    public void lesson46step1() {
        List<String> students = studentRepo.findAll().stream().map(Student::getName).collect(Collectors.toList());

        System.out.println(students);

        System.err.println(students.get(0));
        System.err.println(students.get(1));

        new Thread(() -> {
            printStudents(2, (students.size() - 2) / 2, students, 1);
        }).start();

        new Thread(() -> {
            printStudents((students.size() - 2) / 2, students.size(), students, 3);
        }).start();
    }

    public void lesson46step2() {
        Object syncFlag = new Object();

        List<String> students = studentRepo.findAll().stream().map(Student::getName).collect(Collectors.toList());

        System.out.println(students);

        System.err.println(students.get(0));
        System.err.println(students.get(1));


        new Thread(() -> {
            synchronized (syncFlag) {
                printStudents(2, (students.size() - 2) / 2, students, 1);
            }
        }).start();


        new Thread(() -> {
            synchronized (syncFlag) {
                printStudents((students.size() - 2) / 2, students.size(), students, 3);
            }
        }).start();

    }

    private void printStudents(int startIndex, int endIndex, List<String> students, int timeout) {
        try {
            for (int i = startIndex; i < endIndex; i++) {
                System.err.println(students.get(i));
                Thread.sleep(timeout); //Slows down the process, so output can vary
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
