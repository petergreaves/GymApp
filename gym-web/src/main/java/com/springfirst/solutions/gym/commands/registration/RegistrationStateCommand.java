package com.springfirst.solutions.gym.commands.registration;

import com.springfirst.solutions.gym.domain.registration.Stage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class RegistrationStateCommand {

    private Stage stage;
    private LocalDate created;
    private LocalDate updated;
    private String email;
}
