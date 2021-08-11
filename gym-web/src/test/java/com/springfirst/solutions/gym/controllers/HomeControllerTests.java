package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.services.ImageService;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import org.h2.server.web.WebApp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
public class HomeControllerTests {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    ImageService imageService;

    @MockBean
    TrainerService trainerService;

    @MockBean
    TrainerSpecialityService trainerSpecialityService;

    @InjectMocks
    private TrainerController trainerController ;
    private ImageController imageController;

    MockMvc mockMvc ;

    private HomeController homeController;

    @BeforeEach
    public void setup(){

      mockMvc= MockMvcBuilders.
              webAppContextSetup(webApplicationContext)
              .apply(springSecurity())
              .build();


    }

    @Test
    public void testHomePageMVCAuth_fail() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser("user")
    public void testHomePageMVCAuth_success() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHomePage() {

        homeController = new HomeController();
        String s = homeController.home();
        Assertions.assertEquals(s, "home/index");
    }

    @Test
    public void testHomePageMembersNotImplemented() throws Exception {

        homeController = new HomeController();
        String s = homeController.members();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageRegisterNotImplementedL(){

        homeController = new HomeController();
        String s = homeController.register();
        Assertions.assertEquals(s, "not-implemented");
    }
    @Test
    public void testHomePageClassesNotImplemented() {

        homeController = new HomeController();
        String s = homeController.classes();
        Assertions.assertEquals(s, "not-implemented");

    }

}
