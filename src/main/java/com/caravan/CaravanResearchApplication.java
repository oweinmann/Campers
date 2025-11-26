package com.caravan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application for Caravan Research
 * Supports both standalone and Tomcat 10 deployment
 */
@SpringBootApplication
public class CaravanResearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaravanResearchApplication.class, args);
    }
}
