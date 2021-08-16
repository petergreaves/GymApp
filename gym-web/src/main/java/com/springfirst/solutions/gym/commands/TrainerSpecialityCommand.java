package com.springfirst.solutions.gym.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class TrainerSpecialityCommand {

    private String name;
    private String description;
    private Long id;
}
