package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(AppConfiguration.class)
public class TestAppRunner {
    public static void main(String... args) {
        SpringApplication.run(TestAppRunner.class, args);
    }
}
