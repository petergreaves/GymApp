package com.springfirst.solutions.gym.controllers.registration;

import com.springfirst.solutions.gym.controllers.BaseIT;
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
public class RegistrationControllerMVCSecurityTests extends BaseIT {

    // get a list of all registration requests
    // TODO secure it, this is admin only, with registration grant.
    @Test
    public void registrationsListNotAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/registrations/list"))
                .andExpect(status().isUnauthorized());

    }


    // get the form for a new registration anonymously
    @Test
    public void registrationNewIsAvailableUnauthenticated_success() throws Exception{
        mockMvc.perform(get("/register/new"))
                .andExpect(status().is2xxSuccessful());
    }



}
