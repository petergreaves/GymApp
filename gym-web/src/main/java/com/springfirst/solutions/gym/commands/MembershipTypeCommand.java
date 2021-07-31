package com.springfirst.solutions.gym.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class MembershipTypeCommand {

    private String type;
    private String description;
}
