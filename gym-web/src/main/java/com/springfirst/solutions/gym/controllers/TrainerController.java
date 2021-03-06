package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.exceptions.TrainerDuplicateEmployeeIDException;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    @GetMapping("/{id}/show")
    @ResponseStatus(HttpStatus.OK)
    public String viewTrainer(Model model, @PathVariable("id") String trainerID){

        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(trainerID));
        return "trainers/view-trainer-details";
    }

    //get a list of all trainers
    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public String getTrainers(Model model){

        model.addAttribute("trainers", trainerService.getAllTrainers());
        return "trainers/view-trainers-list";
    }


    // get the form for create a new trainer
    @PreAuthorize("hasAuthority('admin.trainer.create')")
    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public String getCreateTrainerForm(Model model){

        model.addAttribute("trainer", trainerService.getNewTrainerInstance());
        return TRAINER_CREATE_UPDATE_FORM;
    }

    // get the form for updating an existing trainer
    @PreAuthorize("hasAnyAuthority('trainer.trainer.update', 'admin.trainer.update')")
    @GetMapping("/{id}/update")
    @ResponseStatus(HttpStatus.OK)
    public String getUpdateTrainerForm(Model model, @PathVariable("id") String employeeID){

        model.addAttribute("trainer", trainerService.getTrainerByEmployeeID(employeeID));
        return TRAINER_CREATE_UPDATE_FORM;
    }



    // handle the post of a new trainer
    @PreAuthorize("hasAuthority('admin.trainer.create')")
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String createTrainer(@Valid @ModelAttribute("trainer") TrainerCommand trainerCommand,
                                BindingResult bindingResult,
                                Model model){

        if (bindingResult.hasErrors()) {
            for (ObjectError allError : bindingResult.getAllErrors()) {
                log.error("Trainer create/update error validating : " + allError.getDefaultMessage());
            }
            trainerCommand.setIsNew(true); // never been saved
            return TRAINER_CREATE_UPDATE_FORM;
        }

        TrainerCommand savedTrainer;
        try {
             savedTrainer = trainerService.createOrUpdateTrainer(trainerCommand);
        }
        catch(TrainerDuplicateEmployeeIDException tex){
       bindingResult.addError(new FieldError("trainer","employeeID", tex.getMessage()));
            trainerCommand.setIsNew(true); // never been saved
            return TRAINER_CREATE_UPDATE_FORM;

        }
        return "redirect:/trainers/" + savedTrainer.getEmployeeID() + "/show";
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

        TrainerCommand updatedTrainer=trainerService.createOrUpdateTrainer(updatedTrainerCommand);
        return "redirect:/trainers/" + updatedTrainer.getEmployeeID() + "/show";
    }

    // handle delete trainer
    @PreAuthorize("hasAuthority('admin.trainer.delete')")
    @RequestMapping (value = "/{id}/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public String doTrainerDelete(@PathVariable("id") String employeeID){

        log.debug("Got a request to delete trainer with empID {}", employeeID);
        trainerService.deleteTrainer(employeeID);
        return "redirect:/trainers/list";
    }


    @ModelAttribute
    public void specialities(Model model){
        model.addAttribute("specialities",  trainerSpecialityService.getTrainerSpecialities());
    }

}
