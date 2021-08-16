package com.springfirst.solutions.gym.controllers.rest;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainer-specialities")
public class TrainerSpecialitiesRestController {


    private final TrainerSpecialityService trainerSpecialityService;

    public TrainerSpecialitiesRestController(TrainerSpecialityService trainerSpecialityService) {
        this.trainerSpecialityService = trainerSpecialityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrainerSpecialityCommand> getTrainerSpecialities(){

        List<TrainerSpecialityCommand> specialities = trainerSpecialityService.getTrainerSpecialities();
        return specialities;

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrainerSpecialityCommand getTrainerSpecialityByID(@PathVariable("id") Long specID){

        return trainerSpecialityService.getTrainerSpecialityByID(specID);

    }


}
