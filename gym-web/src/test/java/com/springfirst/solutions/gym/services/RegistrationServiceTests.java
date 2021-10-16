package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.exceptions.NoSuchRegistrationException;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import com.springfirst.solutions.gym.services.registration.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class RegistrationServiceTests {


    @Mock
    private RegistrationRepository registrationRepository;

    private RegistrationService registrationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registrationService = new RegistrationServiceImpl(registrationRepository);


    }


    @Test
    public void getARegistrationState_success() {

        RegistrationState state = RegistrationState.builder().id(1L).email("a@foo.com").stage(Stage.PENDING).created(LocalDate.now()).build();
        when(registrationRepository.findRegistrationStateByEmail(any())).thenReturn(Optional.of(state));

        RegistrationState stateFromService = registrationService.findRegistrationByEmail("boo@bar.com").orElseThrow();
        Assertions.assertAll(

                () -> {
                    Assertions.assertNotNull(stateFromService);
                     Assertions.assertEquals(stateFromService.getCreated(),state.getCreated() );
                });

    }

    @Test
    public void getARegistrationStateForNoMatchThrowsNoSuchRegEx_success() {

        when(registrationRepository.findRegistrationStateByEmail(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRegistrationException.class, () -> {
            RegistrationState stateFromService = registrationService.findRegistrationByEmail("boo@bar.com").orElseThrow();
            registrationService.findRegistrationByEmail("XXXX");
        });

        verify(registrationRepository, times(1)).findRegistrationStateByEmail(anyString());

    }

//    @Test
//    public void deleteRegSuccess_success() {
//
//        String regToDeleteEmail = "foo@bar.com";
//        RegistrationState reg = RegistrationState.builder().email(regToDeleteEmail).build();
//
//        RegistrationStateCommand regCommand = RegistrationStateCommand.builder().email(regToDeleteEmail).build();
//
//        when(registrationRepository.findRegistrationStateByEmail(any(String.class))).thenReturn(Optional.of(reg));
//        registrationService.deleteRegistration(regCommand);
//
//        verify(registrationRepository.delete(any(RegistrationState.class));
//      //  verify(registrationService,  times(1)).deleteRegistration(any(RegistrationStateCommand.class));
//
//    }

}