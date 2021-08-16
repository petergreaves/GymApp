package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TrainerControllerMVCTests extends BaseIT{

    @Test
    public void trainersListAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/trainers/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void trainersListNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/images/trainers/{id}/upload", "A9933"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void trainersNewNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/trainers/new"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void trainersPostNewNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(post("/trainers/new")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void trainerShowAvailableUnauthenticated_success() throws Exception{

        TrainerCommand trainerCommand = TrainerCommand.builder()
                .employeeID("A9971")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpecialityCommandID(1L)
                .trainerSpecialityCommandID(2L)
                .biography("Been a personal trainer for 10 years")
                .image(new Byte['3'])
                .build();

        when(trainerService.getTrainerByEmployeeID(ArgumentMatchers.anyString())).thenReturn(trainerCommand);
        mockMvc.perform(get("/trainers/A0002/show"))
                .andExpect(status().isOk());

    }

    @Test
    public void trainerEditNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/trainers/{id}/update", "A0099"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void trainerDeleteNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(post("/trainers/{id}/delete", "A0099")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isUnauthorized());

    }
}
