package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.services.GymService;
import com.springfirst.solutions.gym.services.GymServiceImpl;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.ui.Model;


import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TrainerIT {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GymRepository gymRepository;

    @MockBean
    Model model;


    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private GymMapper gymMapper;

    private TrainerService trainerService;
    private GymService gymService ;
    private TrainerController trainerController;


    @BeforeEach
    public void setup(){

        trainerService = new TrainerServiceImpl(trainerRepository, trainerMapper);
        trainerController = new TrainerController(trainerService);
        gymService = new GymServiceImpl(gymMapper, trainerMapper, gymRepository);

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


    @Test
    public void testListTrainer_success(){

        List<TrainerCommand> trainerCommandList = trainerService.getAllTrainers();
        List<Trainer> fromRepo = trainerRepository.findAll();

        // do these match?

        Assertions.assertEquals(fromRepo.size(), trainerCommandList.size());

    }

    @Test
    @DirtiesContext
    public void testRemoveTrainer_success(){

        Trainer getOne = trainerRepository.findAll().get(0);
        TrainerCommand toRemove = TrainerCommand.builder().employeeNumber(getOne.getEmployeeNumber()).build();
        gymService.removeTrainer(toRemove);
        trainerRepository.delete(getOne);

        TrainerCommand test = trainerService.getTrainerByEmployeeID(toRemove.getEmployeeNumber());

        Assertions.assertNull(test);

    }
}
