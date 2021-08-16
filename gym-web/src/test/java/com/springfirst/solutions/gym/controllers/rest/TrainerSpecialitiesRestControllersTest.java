package com.springfirst.solutions.gym.controllers.rest;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.controllers.BaseIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest()
public class TrainerSpecialitiesRestControllersTest extends BaseIT{

    TrainerSpecialityCommand trainerSpecialityCommand1;
    TrainerSpecialityCommand trainerSpecialityCommand2;

    List<TrainerSpecialityCommand> ret;

    @BeforeEach
    void setUp() {
        super.setup();

        trainerSpecialityCommand1 = TrainerSpecialityCommand.builder()
                .description("spec 1 desc")
                .id(1L)
                .build();

        trainerSpecialityCommand2 =TrainerSpecialityCommand.builder()
                .description("spec 2 desc")
                .id(2L)
                .build();


        ret = new ArrayList<>();

        ret.add(trainerSpecialityCommand1);
        ret.add(trainerSpecialityCommand2);
    }

    @Test
    public void getAllTrainerSpecialityCommands() throws Exception{

        when(trainerSpecialityService.getTrainerSpecialities()).thenReturn(ret);

        mockMvc.perform(get("/api/v1/trainer-specialities"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());

        verify(trainerSpecialityService, times(1)).getTrainerSpecialities();
    }
    @Test
    public void getTrainerSpecialityByID() throws Exception{

        when(trainerSpecialityService.getTrainerSpecialityByID(anyLong())).thenReturn(ret.get(0));
        mockMvc.perform(get("/api/v1/trainer-specialities/{id}","1"))
                .andExpect(jsonPath("$.description", is("spec 1 desc")))
                .andExpect(status().isOk());

        verify(trainerSpecialityService, times(1)).getTrainerSpecialityByID(anyLong());
    }

}
