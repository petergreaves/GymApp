package com.springfirst.solutions.gym.commands.registration;

import com.springfirst.solutions.gym.domain.registration.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegistrationStateCommand {

    private Stage stage;
    private LocalDate created;
    private LocalDate updated;
    private String email;
    private Long id;
    private String name;
    private String password;
}
