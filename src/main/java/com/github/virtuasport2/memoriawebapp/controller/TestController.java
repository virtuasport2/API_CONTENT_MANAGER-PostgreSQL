package com.github.virtuasport2.memoriawebapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}