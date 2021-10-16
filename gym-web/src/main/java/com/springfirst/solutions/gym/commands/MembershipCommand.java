package com.springfirst.solutions.gym.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
public class MembershipCommand {

    private LocalDate start;
    private LocalDate end;
    private Boolean active;


    private MembershipTypeCommand membershipTypeCommand;
}
