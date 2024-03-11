package com.restart.auth.authenticationservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test successful";
    }

    @GetMapping("/test1")
    public String test1() {
        return "test1 successful";
    }

    @GetMapping("/abc")
    public String abc() {
        return "abc successful";
    }
}
