package com.springfirst.solutions.gym.commands;

import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TrainerCommand {

    private String name;
    private String telNo;
    private String employeeID;
    private String biography;
    private String imagePath;
    private Boolean isNew;

    @Singular
    private Set<TrainerSpecialityCommand> trainerSpecialityCommands;
}
