package com.example.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
