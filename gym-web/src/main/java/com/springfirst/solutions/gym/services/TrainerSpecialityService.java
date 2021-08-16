package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface TrainerSpecialityService {

    List<TrainerSpecialityCommand> getTrainerSpecialities();
    TrainerSpecialityCommand getTrainerSpecialityByID(Long id);
}
