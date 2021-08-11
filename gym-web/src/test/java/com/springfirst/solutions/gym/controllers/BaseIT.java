package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.services.ImageService;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class BaseIT {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    protected ImageService imageService;

    @MockBean
    protected TrainerService trainerService;

    @MockBean
    protected TrainerSpecialityService trainerSpecialityService;

    @InjectMocks
    protected TrainerController trainerController ;
    protected ImageController imageController;

    protected MockMvc mockMvc ;

    protected HomeController homeController;

    @BeforeEach
    public void setup(){

        mockMvc= MockMvcBuilders.
                webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();


    }
}
