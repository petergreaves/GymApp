package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TrainerControllerIT {

    @Autowired
    private TrainerRepository trainerRepository;


    @Autowired
    private TrainerMapper trainerMapper;

    private TrainerService trainerService;
    private TrainerController trainerController;


    @BeforeEach
    public void setup(){

        trainerService = new TrainerServiceImpl(trainerRepository, trainerMapper);
        trainerController = new TrainerController(trainerService);
    }

    @Test
    public void testCreateTrainer_success(){

        TrainerCommand newTrainerCommand = TrainerCommand.builder()
                .employeeNumber("A001")
                .name("Big Jim")
                .telNo("838438")
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("strength")
                        .build())
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("yoga")
                        .build())
                .build();

        trainerController.createTrainer(newTrainerCommand);

        Optional<Trainer> newOne = trainerRepository.findByEmployeeNumber("A001");
        Assertions.assertTrue(newOne.isPresent());

    }
}
