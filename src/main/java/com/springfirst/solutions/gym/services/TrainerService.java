package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;

import java.util.List;

public interface TrainerService {

    List<TrainerCommand> getAllTrainers();
    TrainerCommand getTrainerById(Long id);
    TrainerCommand getTrainerByEmployeeID(String empID);

}
