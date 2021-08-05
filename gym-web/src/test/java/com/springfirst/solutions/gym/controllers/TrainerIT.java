package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.mappers.TrainerSpecialityMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.repositories.TrainerSpecialityRepository;
import com.springfirst.solutions.gym.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;


import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TrainerIT {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private TrainerSpecialityRepository trainerSpecialityRepository ;

    @MockBean
    Model model;

    @Mock
    BindingResult bindingResult;

    @Autowired
    private TrainerSpecialityMapper trainerSpecialityMapper;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private GymMapper gymMapper;

    private TrainerService trainerService;
    private TrainerSpecialityService trainerSpecialityService;
    private GymService gymService ;
    private TrainerController trainerController;


    @BeforeEach
    public void setup(){

        trainerService = new TrainerServiceImpl(trainerRepository, trainerMapper);
        trainerSpecialityService = new TrainerSpecialityServiceImpl(trainerSpecialityRepository,trainerSpecialityMapper );
        trainerController = new TrainerController(trainerService, trainerSpecialityService);
        gymService = new GymServiceImpl(gymMapper, trainerMapper, gymRepository);

    }

    @Test
    public void testCreateTrainer_success(){

        TrainerCommand newTrainerCommand = TrainerCommand.builder()
                .employeeID("A0201")
                .name("Big Jim Beefcake")
                .telNo("83843874774")
                .biography("now is the time for all good men to come to the aid of the party")
                .imagePath("/images/p039.jpg")
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("strength")
                        .build())
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("yoga")
                        .build())
                .build();

        trainerController.createTrainer(newTrainerCommand, bindingResult);

        Optional<Trainer> newOne = trainerRepository.findByEmployeeID("A0201");
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
        TrainerCommand toRemove = TrainerCommand.builder().employeeID(getOne.getEmployeeID()).build();
        gymService.removeTrainer(toRemove);
        trainerRepository.delete(getOne);

        List<TrainerCommand> trainers = trainerService.getAllTrainers();
        Assertions.assertTrue(trainers.stream().noneMatch(t -> t.getEmployeeID().equals(toRemove.getEmployeeID())));


    }


}
