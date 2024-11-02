package com.example.encryptor_decryptor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PostMapping("/post")
    public ResponseEntity<String> testPostEndpoint(@RequestBody TestRequest request) {
        // Log or print the received data to verify it
        System.out.println("Received data: " + request);

        // Return a simple response
        return ResponseEntity.ok("POST request received with name: " + request.getName());
    }

    // Sample Request class to hold incoming data
    public static class TestRequest {
        private String name;
        private int age;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "TestRequest{name='" + name + "', age=" + age + '}';
        }
    }
}
