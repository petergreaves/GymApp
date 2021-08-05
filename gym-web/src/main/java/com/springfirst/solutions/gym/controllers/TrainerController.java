package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
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
    private final TrainerSpecialityService trainerSpecialityService;

    public TrainerController(TrainerService trainerService, TrainerSpecialityService trainerSpecialityService) {
        this.trainerService = trainerService;
        this.trainerSpecialityService = trainerSpecialityService;
    }

    // get an individual trainer
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String viewTrainer(Model model, @PathVariable("id") String trainerID){

        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(trainerID));
        return "trainers/view-trainer-details";
    }

    //get a list of all trainers
    @GetMapping({"/", ""})
    @ResponseStatus(HttpStatus.OK)
    public String getTrainers(Model model){

        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/view-trainers-list";
    }


    // get the form for create a new trainer
    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String getCreateTrainerForm(Model model){

        model.addAttribute("specialities", trainerSpecialityService.getTrainerSpecialities());
        model.addAttribute("trainer", trainerService.getNewTrainerInstance());
        return "trainers/create-update-trainer-form";
    }

    // get the form for updating an existing trainer
    @GetMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public String getUpdateTrainerForm(Model model, @PathVariable("id") String employeeID){

        model.addAttribute("specialities", trainerSpecialityService.getTrainerSpecialities());
        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(employeeID));
        return "trainers/create-update-trainer-form";
    }


    // handle the post of a new trainer
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String createTrainer(TrainerCommand newTrainerCommand){

        TrainerCommand savedTrainer=trainerService.createTrainer(newTrainerCommand);
        return "redirect:trainers/view-trainers-list/" + savedTrainer.getEmployeeID();
    }

    // handle the post of an updated  trainer
    @PutMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String updateTrainer(@PathVariable String id, TrainerCommand updatedTrainerCommand){

        TrainerCommand savedTrainer=trainerService.updateTrainer(updatedTrainerCommand);
        return "redirect:trainers/view-trainers-list/" + updatedTrainerCommand.getEmployeeID();
    }

    // handle delete trainer
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String doTrainerDelete(Model model, @PathVariable("id") String employeeID){

       trainerService.deleteTrainer(employeeID);
       return "redirect:trainers/view-trainers-list";
    }
}
