package com.danli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@SpringBootTest
class VueblogApplicationTests {

    @Test
    void contextLoads() {


    }
    @BeforeEach
    void con(){
        String  s = "dfs";
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        String[] array = new String[200];
        Arrays.sort(array, String::compareTo);

        Stream<String> stream = Stream.of("A", "B", "C", "D");
        stream.forEach(System.out::println);


    }


}
