package com.springfirst.solutions.gym.controllers.rest;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.error.Error;
import com.springfirst.solutions.gym.exceptions.TrainerInvalidContentException;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import com.springfirst.solutions.gym.services.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

        try {
            return trainerService.getTrainerByEmployeeID(empID);

        }catch(TrainerNotFoundException tnfe){

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Trainer Not Found with ID "+empID, tnfe);
        }

    }

    @DeleteMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainerByID(@PathVariable("id") String empID){

        trainerService.deleteTrainer(empID);

    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerCommand createTrainer(@Valid @RequestBody TrainerCommand newTrainerCommand, BindingResult bindingResult, HttpServletResponse httpResponse,
                                        WebRequest request){

        if (bindingResult.hasErrors()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request to create trainer",
                    new TrainerInvalidContentException("bad request", bindingResult.getAllErrors()));
        }

        TrainerCommand created = trainerService.createOrUpdateTrainer(newTrainerCommand);

        httpResponse.setStatus(HttpStatus.CREATED.value());
        httpResponse.setHeader("Location", String.format("%s/api/v1/trainers/%s",
                request.getContextPath(), created.getEmployeeID()));

        return created;

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrainerByID(@PathVariable("id") String empID,
                                  @Valid @RequestBody TrainerCommand updatedTrainerCommand,
                                  BindingResult bindingResult, HttpServletResponse httpResponse,
            WebRequest request){

        if (bindingResult.hasErrors()) {

            log.error("Errors in update request body.");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid request to update trainer",
                    new TrainerInvalidContentException("bad request", bindingResult.getAllErrors()));
        }

        trainerService.createOrUpdateTrainer(updatedTrainerCommand);

        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        httpResponse.setHeader("Location", String.format("%s/api/v1/trainers/%s",
                request.getContextPath(), updatedTrainerCommand.getEmployeeID()));

    }
}
