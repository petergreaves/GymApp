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
    public TrainerCommand updateTrainer(TrainerCommand trainerCommand) {
        return null;
    }

    @Override
    public void deleteTrainer(String employeeID) throws TrainerNotFoundException{
        Optional<Trainer> t = trainerRepository.findByEmployeeID(employeeID);

        if (t.isPresent()){
            trainerRepository.delete(t.get());
        }
        else {
            throw new TrainerNotFoundException("Trainer not found with employee ID : "+ employeeID);
        }

    }
}
