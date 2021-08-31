package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.domain.security.User;
import com.springfirst.solutions.gym.exceptions.TrainerDuplicateEmployeeIDException;
import com.springfirst.solutions.gym.exceptions.TrainerInvalidContentException;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.repositories.TrainerSpecialityRepository;
import com.springfirst.solutions.gym.repositories.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final GymService gymService;
    private final TrainerMapper trainerMapper;
    private final TrainerSpecialityRepository trainerSpecialityRepository;
    private final UserRepository userRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository, GymService gymService, TrainerMapper trainerMapper, TrainerSpecialityRepository trainerSpecialityRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.gymService = gymService;
        this.trainerMapper = trainerMapper;
        this.trainerSpecialityRepository = trainerSpecialityRepository;
        this.userRepository = userRepository;
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
            final String message = "Trainer not found with employee ID : "+ empID;
            log.error(message);
            throw new TrainerNotFoundException(message);
        }
    }

    @Override
    public TrainerCommand createOrUpdateTrainer(TrainerCommand trainerCommand) {

        String empID = trainerCommand.getEmployeeID();

        if (trainerCommand.getIsNew()){

            if (trainerRepository.findByEmployeeID(trainerCommand.getEmployeeID()).isPresent()){
                final String message = "Trainer already existswith employee ID : "+ empID;
                log.error(message);
                throw new TrainerDuplicateEmployeeIDException(message);
            }
        }
        Trainer toBeSaved = trainerMapper.trainerCommandToTrainer(trainerCommand);

        // now add the trainerSpecialities from the command as the Mapper doesnt do that
        // the TrainerSpecialities will be an empty Set

        Set<TrainerSpeciality> specialities = new HashSet<>();
        trainerCommand.getTrainerSpecialityCommandIDs()
                .stream()
                .forEach(tsid ->{
                    Optional<TrainerSpeciality> ts = trainerSpecialityRepository.findById(tsid);
                    if (ts.isPresent()){
                        specialities.add(ts.get());
                    }
                });
        toBeSaved.setTrainerSpecialities(specialities);

        Trainer savedTrainer = trainerRepository.save(toBeSaved);
        return trainerMapper.trainerToTrainerCommand(savedTrainer);
    }

    @Override
    public TrainerCommand getNewTrainerInstance() {
        return TrainerCommand.builder().isNew(true).build();
    }


    @Override
    public void deleteTrainer(String employeeID) throws TrainerNotFoundException{
        log.info("Request to delete trainer with emp ID : " +employeeID);
        Optional<Trainer> trainerOptional = trainerRepository.findByEmployeeID(employeeID);

        if (trainerOptional.isPresent()){
            Trainer trainer = trainerOptional.get();

            Optional<User> userToRemove = userRepository.findByUsername(trainer.getEmail());

            if (userToRemove.isPresent()){
                User user = userToRemove.get();
                log.info("Found user with username : " +user.getUsername());

                user.setTrainer(null);
                log.info("Removed trainer id from user : " +user.getUsername());
                userRepository.saveAndFlush(user);

                //   getOne.setUser(null);
            }
            gymService.removeTrainer(trainerMapper.trainerToTrainerCommand(trainer));
            trainerRepository.delete(trainer);
        }
        else {
            final String message = "Trainer not found with employee ID : "+ employeeID;
            log.error(message);
            throw new TrainerNotFoundException(message);
        }

    }
}
