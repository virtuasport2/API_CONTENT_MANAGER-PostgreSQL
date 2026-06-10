package com.github.virtuasport2.memoriawebapp.controller;

@RestController
public class TestController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}