package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller()
@RequestMapping("/trainers")
@Slf4j
public class TrainerController {


    private static final String TRAINER_CREATE_UPDATE_FORM = "trainers/create-update-trainer-form";

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

        model.addAttribute("trainer", trainerService.getNewTrainerInstance());
        return TRAINER_CREATE_UPDATE_FORM;
    }

    // get the form for updating an existing trainer
    @GetMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public String getUpdateTrainerForm(Model model, @PathVariable("id") String employeeID){

        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(employeeID));
        return TRAINER_CREATE_UPDATE_FORM;
    }

    @ModelAttribute
    public void specialities(Model model){
        model.addAttribute("specialities", trainerSpecialityService.getTrainerSpecialities());
    }


    // handle the post of a new trainer
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String createTrainer(@Valid @ModelAttribute("trainer") TrainerCommand trainer, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {

            for (ObjectError allError : bindingResult.getAllErrors()) {
                log.error("Trainer create/update error validating : " + allError.getDefaultMessage());
            }
            trainer.setIsNew(true); // never been saved
            return TRAINER_CREATE_UPDATE_FORM;
        }

        TrainerCommand savedTrainer=trainerService.createTrainer(trainer);
        return "redirect:/trainers/" + savedTrainer.getEmployeeID();
    }

    // handle the post of an updated  trainer
    @PostMapping(value = "/{id}/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String updateTrainer(@Valid @ModelAttribute("trainer") TrainerCommand updatedTrainerCommand, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {

            for (ObjectError allError : bindingResult.getAllErrors()) {
                log.error("Trainer create/update error validating : " + allError.getDefaultMessage());
            }
            updatedTrainerCommand.setIsNew(false); // has been saved
            return TRAINER_CREATE_UPDATE_FORM;
        }

        TrainerCommand updatedTrainer=trainerService.updateTrainer(updatedTrainerCommand);
        return "redirect:/trainers/" + updatedTrainer.getEmployeeID();
    }

    // handle delete trainer
    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String doTrainerDelete(Model model, @PathVariable("id") String employeeID){

       trainerService.deleteTrainer(employeeID);
       return "redirect:trainers/view-trainers-list";
    }
}
