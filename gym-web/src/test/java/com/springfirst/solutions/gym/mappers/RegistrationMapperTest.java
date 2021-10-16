package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;

import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class RegistrationMapperTest {

    @Autowired
    private RegistrationMapper registrationMapper;

    @Test
    public void toCommand(){

        final LocalDate created = LocalDate.now();
        final LocalDate updated = LocalDate.now();
        final Long id = 1L;
        final String email = "foo@bar.com";

        RegistrationState regState = RegistrationState
                .builder()
                .created(created)
                .updated(updated)
                .id(id)
                .stage(Stage.PENDING)
                .email(email)
                .build();

        RegistrationStateCommand regCommand = registrationMapper.registrationStateToRegistrationStateCommand(regState);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(regCommand.getCreated(), regState.getCreated());},
                () -> {Assertions.assertEquals(regCommand.getUpdated(), regState.getUpdated());},
                () -> {Assertions.assertEquals(regCommand.getStage(), regState.getStage());},
                () -> {Assertions.assertEquals(regCommand.getEmail(), regState.getEmail());}
        );
    }

    @Test
    public void toEntity() {

        final LocalDate created = LocalDate.now();
        final LocalDate updated = LocalDate.now();
        final Long id = 1L;
        final String email = "foo@bar.com";

        RegistrationStateCommand registrationStateCommand = RegistrationStateCommand
                .builder()
                .created(created)
                .updated(updated)
                .id(id)
                .stage(Stage.PENDING)
                .email(email)
                .build();

        RegistrationState regState = registrationMapper.registrationStateCommandToRegistrationState(registrationStateCommand);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(registrationStateCommand.getCreated(), regState.getCreated());
                },
                () -> {
                    Assertions.assertEquals(registrationStateCommand.getUpdated(), regState.getUpdated());
                },
                () -> {
                    Assertions.assertEquals(registrationStateCommand.getStage(), regState.getStage());
                },
                () -> {
                    Assertions.assertEquals(registrationStateCommand.getEmail(), regState.getEmail());
                },
                () -> {
                    Assertions.assertEquals(registrationStateCommand.getId(), regState.getId());
                }
        );
    }
}
