package com.springfirst.solutions.gym.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/index", "/index.html"})
public class HomeController {

    @GetMapping
    public String home(){

        return "home/index";
    }

    @GetMapping("/classes")
    public String classes(){

        return "not-implemented";
    }

    @GetMapping("/register")
    public String register(){

        return "not-implemented";
    }
}
