package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;

import java.util.List;


public interface TrainerSpecialityService {

    List<TrainerSpecialityCommand> getTrainerSpecialities();
    TrainerSpecialityCommand getTrainerSpecialityByID(Long id);
}
