package com.springfirst.solutions.gym.commands.registration;

import com.springfirst.solutions.gym.domain.registration.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RegistrationStateCommand {

    private Stage stage;
    private LocalDate created;
    private LocalDate updated;

    @Builder.Default
    private Boolean isNew = true;

    @Email
    private String email;

    @NotBlank
    @Size(min = 7, max = 12)
    private String password;

    private Long id;

}
