package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.services.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
public class HomeController {

    private final GymService gymService;

    public HomeController(GymService gymService) {
        this.gymService = gymService;
    }

    @GetMapping({"/", "/index", "/index.html",""})
    @ResponseStatus(HttpStatus.OK)
    public String home(Model model){

        model.addAttribute("gym", gymService.getGym());
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
