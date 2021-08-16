package com.springfirst.solutions.gym.controllers.rest;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.services.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
@Slf4j
public class TrainerRestController {

    private final TrainerService trainerService;

    public TrainerRestController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    /**
     *
     * GET all trainers
     */

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrainerCommand> getTrainers(){

        List<TrainerCommand> trainers = trainerService.getAllTrainers();
        return trainers;

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrainerCommand getTrainerByID(@PathVariable("id") String empID){

       return trainerService.getTrainerByEmployeeID(empID);

    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainerByID(@PathVariable("id") String empID){

        trainerService.deleteTrainer(empID);

    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerCommand createTrainer(@RequestBody TrainerCommand newTrainerCommand, HttpServletResponse httpResponse,
                              WebRequest request){

        TrainerCommand created = trainerService.createOrUpdateTrainer(newTrainerCommand);

        httpResponse.setStatus(HttpStatus.CREATED.value());
        httpResponse.setHeader("Location", String.format("%s/api/v1/trainers/%s",
                request.getContextPath(), created.getEmployeeID()));

        return created;

    }

    @PutMapping("/{id}/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrainerByID(@PathVariable("id") String empID, @RequestBody TrainerCommand updatedTrainerCommand, HttpServletResponse httpResponse,
            WebRequest request){

        trainerService.createOrUpdateTrainer(updatedTrainerCommand);

        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        httpResponse.setHeader("Location", String.format("%s/api/v1/trainers/%s",
                request.getContextPath(), updatedTrainerCommand.getEmployeeID()));

    }
}
