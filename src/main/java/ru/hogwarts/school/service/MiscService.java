package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MiscService {

    public MiscService() {
    }

    public int lesson45Step4() {
        long start = System.currentTimeMillis();
        long end;
        int sum = Stream
                .iterate(1, a -> a +1)
                .limit(1_000_000)
                .mapToInt(e -> e)
                .sum();
        end = System.currentTimeMillis();
        System.err.println(end - start);
        return sum;
    }

}
