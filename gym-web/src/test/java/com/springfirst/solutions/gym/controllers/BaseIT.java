package com.springfirst.solutions.gym.controllers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.controllers.rest.TrainerRestController;
import com.springfirst.solutions.gym.services.GymService;
import com.springfirst.solutions.gym.services.ImageService;
import com.springfirst.solutions.gym.services.TrainerService;
import com.springfirst.solutions.gym.services.TrainerSpecialityService;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class BaseIT {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    protected ImageService imageService;

    @MockBean
    protected GymService gymService;

    @MockBean
    protected RegistrationService registrationService;

    @MockBean
    protected TrainerService trainerService;

    @MockBean
    protected TrainerSpecialityService trainerSpecialityService;

    @InjectMocks
    protected TrainerController trainerController ;
    protected ImageController imageController;
    protected TrainerRestController trainerRestController;
    protected RegistrationController registrationController;

    @InjectMocks
    protected TrainerRestController trainerSpecialitiesRestController;

    protected MockMvc mockMvc ;

    protected HomeController homeController;

    protected TrainerCommand newInstance;
    protected TrainerCommand trainerCommand;

    @BeforeEach
    public void setup(){

        mockMvc= MockMvcBuilders.
                webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();


        newInstance = TrainerCommand.builder().isNew(true).build();

        trainerCommand = TrainerCommand.builder()
                .employeeID("A9971")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpecialityCommandID(1L)
                .trainerSpecialityCommandID(2L)
                .biography("Been a personal trainer for 10 years")
                .image(new Byte['3'])
                .build();


    }
}
