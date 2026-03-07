package com.bavesh.foodapp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContrller {
    @GetMapping("/")
    public String hello() {
        return "Hello Food App!";
    }
}