package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.mappers.TrainerSpecialityMapper;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTests {

    @Mock
    TrainerService trainerService;

    @Mock
    TrainerSpecialityService trainerSpecialityService;

    @InjectMocks
    TrainerController trainerController;

    MockMvc mockMvc;

    private List<TrainerCommand> trainerCommandsList;
    private List<TrainerSpecialityCommand> trainerSpecialityCommands;

    private TrainerMapper trainerMapper;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(trainerController)
                .build();

        trainerMapper = TrainerMapper.INSTANCE;

        trainerCommandsList = new ArrayList<>();
        trainerCommandsList.add(trainerMapper.trainerToTrainerCommand(Trainer.builder()
                .name("Joe Smith")
                .telNo("049939-8129993")
                .employeeID("AB002")
                .id(9500L)
                .build()));


        trainerCommandsList.add(trainerMapper.trainerToTrainerCommand(Trainer.builder()
                .name("Kelly Strong")
                .telNo("043239-8129993")
                .employeeID("BC889")
                .id(748L)
                .build()));

        trainerSpecialityCommands = new ArrayList<>();

        trainerSpecialityCommands.add(TrainerSpecialityCommand.builder().id(1L).description("yoga").build());
        trainerSpecialityCommands.add(TrainerSpecialityCommand.builder().id(1L).description("classes").build());


    }

    @Test
    public void getAllTrainers() throws Exception {

        when(trainerService.getAllTrainers()).thenReturn(trainerCommandsList);

        mockMvc.perform(get("/trainers/list"))
                .andExpect(model().attributeExists("trainers"))
                .andExpect(model().attribute("trainers", hasSize(2)))
                .andExpect(view().name("trainers/view-trainers-list"))
                .andExpect(status().isOk());

    }


    @Test
    public void getTrainerById() throws Exception {

        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(trainerCommandsList.get(0));

        mockMvc.perform(get("/trainers/{id}/show", "8699"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attribute("trainer", hasProperty("telNo", is("049939-8129993"))))
                .andExpect(view().name("trainers/view-trainer-details"))
                .andExpect(status().isOk());

        verify(trainerService, times(1)).getTrainerByEmployeeID((any()));
    }

    @Test
    public void createTrainer_success() throws Exception {
        TrainerCommand newTrainerCommand = TrainerCommand.builder()
                .employeeID("A001")
                .name("Big Jim")
                .telNo("838438")
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("strength")
                        .build())
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("yoga")
                        .build())
                .build();

        when(trainerService.createTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(newTrainerCommand);

        mockMvc.perform(post("/trainers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Big Jim")
                .param("employeeID", "A1001")
                .param("imagePath", "/images/A002.jpg")
                .param("telNo", "4884988438")
                .param("biography", "this is the story of Big Jim")
                .param("speciality", "yoga", "strength"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());

        verify(trainerService, times(1)).createTrainer(any());
    }

    @Test
    public void createTrainer_failsValidation() throws Exception {
        TrainerCommand newTrainerCommand = TrainerCommand.builder()
                .employeeID("A001")
                .name("Big Jim")
                .telNo("838438")
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("strength")
                        .build())
                .trainerSpecialityCommand(TrainerSpecialityCommand.builder()
                        .description("yoga")
                        .build())
                .build();

      //  when(trainerService.createTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(newTrainerCommand);

        mockMvc.perform(post("/trainers/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "") // blank
                .param("employeeID", "A01") //too short
                .param("imagePath", "/images/A002.jpg")  //ok
                .param("telNo", "48849884274576486838") //too long
                .param("biography", "this is the story of Big Jim")
                .param("speciality", "yoga", "strength"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().hasErrors())
                .andExpect(status().is3xxRedirection());

        verifyNoInteractions(trainerService);
    }


    @Test
    public void deleteTrainer_success() throws Exception {

        //   when(trainerService.deleteTrainer(anyString())).;

        mockMvc.perform(post("/trainers/{id}/delete", "A001")
                .param("id", "A001"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void getCreateTrainerForm_success() throws Exception {

        TrainerCommand newInstance = TrainerCommand.builder().isNew(true).build();

        when(trainerService.getNewTrainerInstance()).thenReturn(newInstance);
        when(trainerSpecialityService.getTrainerSpecialities()).thenReturn(trainerSpecialityCommands);

        mockMvc.perform(get("/trainers/new"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attributeExists("specialities"))
                .andExpect(model().attribute("trainer", hasProperty("isNew", is(true))))
                .andExpect(view().name("trainers/create-update-trainer-form"))
                .andExpect(status().isOk());

        verify(trainerService, times(1)).getNewTrainerInstance();
    }

    @Test
    public void getUpdateTrainerForm_success() throws Exception {

        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(trainerCommandsList.get(0));
        when(trainerSpecialityService.getTrainerSpecialities()).thenReturn(trainerSpecialityCommands);

        mockMvc.perform(get("/trainers/{id}/update", "A001"))
                .andExpect(model().attributeExists("trainer"))
                .andExpect(model().attributeExists("specialities"))
                .andExpect(model().attribute("trainer", hasProperty("isNew", nullValue())))
                .andExpect(view().name("trainers/create-update-trainer-form"))
                .andExpect(status().isOk());

        verify(trainerService, times(1)).getTrainerByEmployeeID(anyString());
    }

    @Test
    public void updateTrainer_success() throws Exception {
        TrainerCommand updatedTrainerCommand = TrainerCommand.builder().build();

        when(trainerService.updateTrainer(ArgumentMatchers.any(TrainerCommand.class))).thenReturn(updatedTrainerCommand);

        mockMvc.perform(post("/trainers/{id}/update", "A001")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Big Jim")
                .param("employeeID", "A0301")
                .param("biography", "now is the time for all good men to come to the aid of the party.")
                .param("telNo", "8384389993")
                .param("id", "1")
                .param("imagePath", "/images/a.jpg")
                .param("speciality", "yoga", "strength"))
                .andExpect(status().is3xxRedirection());

        verify(trainerService, times(1)).updateTrainer(ArgumentMatchers.any(TrainerCommand.class));
    }

}
