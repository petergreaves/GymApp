package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public List<TrainerCommand> getAllTrainers() {

        return trainerRepository
                .findAll()
                .stream()
                .map(trainer -> {
                    TrainerCommand command = TrainerMapper.INSTANCE.trainerToTrainerCommand(trainer);
                    return command;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainerCommand getTrainerById(Long id) {
        return null;
    }

    @Override
    public TrainerCommand getTrainerByEmployeeID(String empID) {
        return TrainerMapper.INSTANCE.trainerToTrainerCommand(trainerRepository.findByEmployeeNumber(empID).get());
    }
}
