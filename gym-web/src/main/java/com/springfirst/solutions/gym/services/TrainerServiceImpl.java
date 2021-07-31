package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public List<TrainerCommand> getAllTrainers() {

        return trainerRepository
                .findAll()
                .stream()
                .map(trainer -> {
                    TrainerCommand command = trainerMapper.trainerToTrainerCommand(trainer);
                    return command;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainerCommand getTrainerById(Long id) {
        return trainerMapper.trainerToTrainerCommand(trainerRepository.findById(id).get());
    }

    @Override
    public TrainerCommand getTrainerByEmployeeID(String empID) {
        return trainerMapper.trainerToTrainerCommand(trainerRepository.findByEmployeeNumber(empID).get());
    }

    @Override
    public TrainerCommand createTrainer(TrainerCommand trainerCommand) {

        Trainer savedTrainer = trainerRepository.save(trainerMapper.trainerCommandToTrainer(trainerCommand));
        return trainerMapper.trainerToTrainerCommand(savedTrainer);

    }
}
