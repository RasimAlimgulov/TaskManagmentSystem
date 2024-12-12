package org.example.taskmanagmentsystem.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSSLController {
    @RequestMapping("/test")
    public String test(){
        return "Test working!";
    }
}
