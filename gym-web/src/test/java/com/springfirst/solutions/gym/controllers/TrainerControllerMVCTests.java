package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class TrainerControllerMVCTests extends BaseIT{

    @Test
    public void trainersListAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/trainers/list"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    public void trainersImageUploadNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/images/trainers/{id}/upload", "A9933"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void trainersNewNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/trainers/new"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser(username = "foo", password = "bar", authorities = {"admin.trainer.create"})
    public void trainersNewAvailableToUserInRole_success() throws Exception{

        when(trainerService.getNewTrainerInstance()).thenReturn(newInstance);
        mockMvc.perform(get("/trainers/new"))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(authorities = {"not.valid"})
    public void trainersPostNewNotAvailable_success() throws Exception{
        mockMvc.perform(post("/trainers/new")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(authorities = {"admin.trainer.create"})
    public void trainersPostNewIsAvailableToRole_success() throws Exception{
        mockMvc.perform(post("/trainers/new")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void trainerShowAvailableUnauthenticated_success() throws Exception{

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
    @WithMockUser(authorities = {"admin.trainer.update", "trainer.trainer.update"})
    public void trainerEditIsAuthorityProtected_success() throws Exception{

        newInstance.setIsNew(false);
        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(newInstance);

        mockMvc.perform(get("/trainers/{id}/update", "A0099"))
                .andExpect(status().is2xxSuccessful());

    }
    @Test
    @WithMockUser(authorities = {"not.valid"})
    public void trainerEditIsAuthorityProtected_fail() throws Exception{

        newInstance.setIsNew(false);
        when(trainerService.getTrainerByEmployeeID(anyString())).thenReturn(newInstance);

        mockMvc.perform(get("/trainers/{id}/update", "A0099"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(authorities = {"admin.trainer.delete"})
    public void trainerDeleteIsAuthorityProtected_success() throws Exception{
        mockMvc.perform(post("/trainers/{id}/delete", "A0001")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection());

    }
    @Test
    @WithMockUser(authorities = {"not.valid"})
    public void trainerDeleteIsAuthorityProtected_fail() throws Exception{
        mockMvc.perform(post("/trainers/{id}/delete", "A0001")
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isForbidden());

    }
}
