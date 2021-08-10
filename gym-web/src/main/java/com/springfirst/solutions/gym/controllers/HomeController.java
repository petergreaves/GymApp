package com.springfirst.solutions.gym.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
public class HomeController {

    @GetMapping({"/", "/index", "/index.html",""})
    @ResponseStatus(HttpStatus.OK)
    public String home(){

        return "home/index";
    }

    @GetMapping("/classes")
    @ResponseStatus(HttpStatus.OK)
    public String classes(){

        return "not-implemented";
    }

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(){

        return "not-implemented";
    }

    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public String members(){

        return "not-implemented";
    }
}
