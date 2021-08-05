package com.springfirst.solutions.gym.commands;

import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TrainerCommand {

    @NotBlank
    @Size(min = 3, max = 32)
    private String name;

    @NotBlank
    @Size(min = 7, max = 12)
    private String telNo;

    @NotBlank
    @Size(min=5, max=5)
    private String employeeID;

    @NotBlank
    @Size(min = 12, max = 255)
    private String biography;

    @NotBlank
    private String imagePath;
    private Boolean isNew;

    @Singular
    private Set<TrainerSpecialityCommand> trainerSpecialityCommands;
}
