package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.services.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String viewTrainer(Model model, @PathVariable("id") String trainerID){

        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(trainerID));
        return "trainers/view-trainer-details";
    }

    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public String getTrainers(Model model){

        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/view-trainers-list";
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String createTrainer(TrainerCommand newTrainerCommand){

        TrainerCommand savedTrainer=trainerService.createTrainer(newTrainerCommand);
        return "redirect:trainers/view-trainers-list/" + savedTrainer.getEmployeeID();
    }
}
