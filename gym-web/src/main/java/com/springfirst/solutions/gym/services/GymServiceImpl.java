package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GymServiceImpl implements GymService {

    private final GymMapper gymMapper;
    private final TrainerMapper trainerMapper;
    private final GymRepository gymRepository;

    public GymServiceImpl(GymMapper gymMapper, TrainerMapper trainerMapper, GymRepository gymRepository) {
        this.gymMapper = gymMapper;
        this.trainerMapper = trainerMapper;
        this.gymRepository = gymRepository;
    }

    @Override
    public GymCommand getGym() {

        // TODO handle no gym by throwing exception

        Gym g = gymRepository.findFirstByGymInfoNotNull().get();
        return gymMapper.gymToGymCommand(g);
    }

    @Override
    public Set<TrainerCommand> removeTrainer(TrainerCommand trainerCommand) {

        Gym theGym = gymRepository
                .findFirstByGymInfoNotNull()
                .get();

        Set<Trainer> updatedTrainers = theGym
                .getTrainers()
                .stream()
                .filter(t -> !t.getEmployeeID().equals(trainerCommand.getEmployeeID()))
                .collect(Collectors.toSet());

        theGym.setTrainers(updatedTrainers);
        gymRepository.save(theGym);

        return updatedTrainers
                .stream()
                .map(t -> {
                    TrainerCommand command = trainerMapper.trainerToTrainerCommand(t);
                    return command;
                }).collect(Collectors.toSet());

    }
}
