package com.epam.brest.courses.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Configuration
@SpringBootConfiguration
@SpringBootApplication(scanBasePackages = {"com.epam.brest.courses*"})
@ComponentScan(basePackages = "com.epam.brest.courses*")
@EntityScan(basePackages = "com.epam.brest.courses.model")
@ImportResource(locations = {"classpath*:prod-db.xml", "classpath*:test-db.xml", "classpath*:dao.xml"})
public class RestApp {
    public static void main(String[] args) {

        SpringApplication.run(RestApp.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
