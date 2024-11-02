package com.example.encryptor_decryptor.service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultHomeRoute {
    @GetMapping("/")
    public String home() {
        return "Welcome to the Home Page!";
    }
}
