package com.eedrpra.my_simple_app.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://eedrpra-my-simple-app-website.s3-website.ap-southeast-3.amazonaws.com")
public class SimpleController {

    @GetMapping("/api/hello")
    public String mySimpleHello() {
        return "Hello World, this is version V1..happy learning friends";
    }

    @GetMapping("/")
    public String index() {
        return "Hello from Spring Boot WAR on Beanstalk!";
    }
}
