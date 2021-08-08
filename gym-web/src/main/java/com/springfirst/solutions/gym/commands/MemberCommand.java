package com.springfirst.solutions.gym.commands;

import com.springfirst.solutions.gym.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class MemberCommand {

    private String telNo;
    private String name;
    private String memberID;
    private LocalDate dateOfBirth;
    private String trainingGoals;
    private Byte[] image;
    private Set<VisitCommand> visitCommands;
    @Singular
    private Set<MembershipCommand> membershipCommands;
}
