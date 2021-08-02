package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.commands.TrainerCommand;

import java.util.Set;

public interface GymService {

    GymCommand getGym();
    Set<TrainerCommand> removeTrainer(TrainerCommand trainerCommand);

}
