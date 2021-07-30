package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.services.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTests {

    @Mock
    TrainerService trainerService;

    @InjectMocks
    TrainerController trainerController;

    MockMvc mockMvc;

    private List<TrainerCommand> trainerCommandsList;

    private TrainerMapper trainerMapper;

    @BeforeEach
    public void setup(){

        mockMvc = MockMvcBuilders
                .standaloneSetup(trainerController)
                .build();

        trainerMapper = TrainerMapper.INSTANCE;

        trainerCommandsList = new ArrayList<>();
        trainerCommandsList.add(trainerMapper.trainerToTrainerCommand(Trainer.builder()
                .name("Joe Smith")
                .telNo("049939-8129993")
                .employeeNumber("AB002")
                .id(9500L)
                .build()));


        trainerCommandsList.add(trainerMapper.trainerToTrainerCommand(Trainer.builder()
                .name("Kelly Strong")
                .telNo("043239-8129993")
                .employeeNumber("BC889")
                .id(748L)
                .build()));

    }

    @Test
    public void getAllTrainers() throws Exception {

        when(trainerService.getAllTrainers()).thenReturn(trainerCommandsList);

        mockMvc.perform(get("/trainers"))
                .andExpect(model().attributeExists("trainers"))
                .andExpect(model().attribute("trainers",hasSize(2) ))
                .andExpect(view().name("trainers/view-trainers-list"))
                .andExpect(status().isOk());

    }


    @Test
    public void getTrainerById() throws Exception {

        when(trainerService.getTrainerById(anyLong())).thenReturn(trainerCommandsList.get(0));

        mockMvc.perform(get("/trainers/{id}", "8699"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attribute("trainer", hasProperty("telNo", is("049939-8129993"))))
                .andExpect(view().name("trainers/view-trainer-details"))
                .andExpect(status().isOk());
    }

}
