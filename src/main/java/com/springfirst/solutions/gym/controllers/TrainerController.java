package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.services.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller()
@RequestMapping("/trainers")
@Slf4j
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String viewTrainer(Model model, @PathVariable("id") Long trainerID){

        model.addAttribute("trainer", trainerService.getTrainerById(trainerID));
        return "trainers/view-trainer-details";
    }

    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public String getTrainers(Model model){

        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/view-trainers-list";
    }
}
