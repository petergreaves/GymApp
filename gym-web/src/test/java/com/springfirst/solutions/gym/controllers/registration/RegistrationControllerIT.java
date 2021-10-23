package com.springfirst.solutions.gym.controllers.registration;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.controllers.RegistrationController;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import com.springfirst.solutions.gym.services.registration.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class RegistrationControllerIT {

    @Autowired
    private  RegistrationController registrationController;

    private  RegistrationService  registrationService;

    @Autowired
    private RegistrationRepository registrationRepository;


    @MockBean
    Model model;

    @Mock
    BindingResult bindingResult;


    @BeforeEach
    public void setup(){

        RegistrationMapper mapper = RegistrationMapper.INSTANCE;
        registrationService = new RegistrationServiceImpl(registrationRepository, mapper);


    }


    @Test
    public void testCreateRegistration_success(){

        final String email="jim@foo.com";
        final String password="pa55w0rd";

        RegistrationStateCommand newRegCommand = RegistrationStateCommand.builder()
                .email(email)
                .password(password)
                .build();

        registrationController.handleNewRegForm(newRegCommand, bindingResult, model);

        RegistrationStateCommand savedCommand = registrationService.findRegistrationByEmail(email);
        Assertions.assertAll(
                () -> {Assertions.assertTrue(savedCommand.getEmail().equals(email));},
                () -> {Assertions.assertTrue(savedCommand.getPassword().equals(password));},
                () -> {Assertions.assertEquals(savedCommand.getStage(), Stage.PENDING);}

        );

    }
}
