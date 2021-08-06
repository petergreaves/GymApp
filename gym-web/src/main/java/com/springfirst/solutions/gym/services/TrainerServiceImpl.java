package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymService gymService;
    private final TrainerMapper trainerMapper;

    public TrainerServiceImpl(TrainerRepository trainerRepository, GymService gymService, TrainerMapper trainerMapper) {
        this.trainerRepository = trainerRepository;
        this.gymService = gymService;
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
    public TrainerCommand getTrainerByEmployeeID(String empID) throws TrainerNotFoundException {

        Optional<Trainer> t = trainerRepository.findByEmployeeID(empID);

        if (t.isPresent()){
            return trainerMapper.trainerToTrainerCommand(t.get());
        }
        else {
            throw new TrainerNotFoundException("Trainer not found with employee ID : "+ empID);
        }
    }

    @Override
    public TrainerCommand createTrainer(TrainerCommand trainerCommand) {
        Trainer savedTrainer = trainerRepository.save(trainerMapper.trainerCommandToTrainer(trainerCommand));
        return trainerMapper.trainerToTrainerCommand(savedTrainer);
    }

    @Override
    public TrainerCommand getNewTrainerInstance() {
        return TrainerCommand.builder().isNew(true).build();
    }

    @Override
    public TrainerCommand updateTrainer(TrainerCommand trainerCommand) {

        Trainer savedTrainer = trainerRepository.save(trainerMapper.trainerCommandToTrainer(trainerCommand));
        return trainerMapper.trainerToTrainerCommand(savedTrainer);
    }

    @Override
    public void deleteTrainer(String employeeID) throws TrainerNotFoundException{
        Optional<Trainer> t = trainerRepository.findByEmployeeID(employeeID);

        if (t.isPresent()){
            Trainer trainer = t.get();
            gymService.removeTrainer(trainerMapper.trainerToTrainerCommand(trainer));
            trainerRepository.delete(trainer);
        }
        else {
            throw new TrainerNotFoundException("Trainer not found with employee ID : "+ employeeID);
        }

    }
}
