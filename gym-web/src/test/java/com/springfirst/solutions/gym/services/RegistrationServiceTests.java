package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.exceptions.NoSuchRegistrationException;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;
import com.springfirst.solutions.gym.services.registration.RegistrationService;
import com.springfirst.solutions.gym.services.registration.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class RegistrationServiceTests {


    @Mock
    private RegistrationRepository registrationRepository;
    private RegistrationService registrationService;

    @Autowired
    private RegistrationMapper registrationMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        registrationService = new RegistrationServiceImpl(registrationRepository, registrationMapper);

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

    @Test
    public void deleteRegSuccess_success() {

        String regToDeleteEmail = "foo@bar.com";
        RegistrationStateCommand regCommand = RegistrationStateCommand.builder().email(regToDeleteEmail).build();
        when(registrationRepository.findRegistrationStateByEmail(any()))
                .thenReturn(Optional.of(RegistrationState.builder().build()));
        registrationService.deleteRegistration(regCommand);

        verify(registrationRepository).delete(any(RegistrationState.class));

    }

    @Test
    public void deleteRegBadEmail_fail() {

        String regToDeleteEmail = "foo@bar.com";
        RegistrationStateCommand regCommand = RegistrationStateCommand.builder().email(regToDeleteEmail).build();
        when(registrationRepository.findRegistrationStateByEmail(any()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchRegistrationException.class, () -> {
            registrationService.deleteRegistration(regCommand);
        });

        verify(registrationRepository, times(0)).delete(any(RegistrationState.class));


    }

    @Test
    public void updateStageState_success() {

        RegistrationState regApproved = RegistrationState.builder().id(1L).email("a@foo.com").stage(Stage.APPROVED).created(LocalDate.now()).build();
        RegistrationState regPending = RegistrationState
                .builder()
                .stage(Stage.PENDING)
                .id(1L)
                .email("a@foo.com").build();

        when(registrationRepository.findRegistrationStateByEmail(any())).thenReturn(Optional.of(regPending));
        when(registrationRepository.save(any())).thenReturn(regApproved);

        RegistrationStateCommand regCommandAfter
                =registrationService.setRegistrationState(registrationMapper.registrationStateToRegistrationStateCommand(regPending), Stage.APPROVED);

        Assertions.assertEquals(regCommandAfter.getStage(), Stage.APPROVED);

        verify(registrationRepository, times(1)).findRegistrationStateByEmail(any(String.class));

    }
}