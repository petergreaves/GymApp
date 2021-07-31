package com.springfirst.solutions.gym.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
public class VisitCommand {
    private LocalDateTime visitDateTime;
    private String purposeOfVisit;
}
