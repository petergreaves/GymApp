package com.springfirst.solutions.gym.commands;

import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Set;

@Builder
@Getter
@Setter
public class TrainerCommand {
    private String name;

    @Singular
    private Set<TrainerSpecialityCommand> trainerSpecialityCommands;
}
