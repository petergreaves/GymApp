package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.mappers.TrainerSpecialityMapper;
import com.springfirst.solutions.gym.repositories.TrainerSpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainerSpecialityServiceImpl implements TrainerSpecialityService {

    private final TrainerSpecialityRepository trainerSpecialityRepository;
    private final TrainerSpecialityMapper trainerSpecialityMapper;

    public TrainerSpecialityServiceImpl(TrainerSpecialityRepository trainerSpecialityRepository, TrainerSpecialityMapper trainerSpecialityMapper) {
        this.trainerSpecialityRepository = trainerSpecialityRepository;
        this.trainerSpecialityMapper = trainerSpecialityMapper;
    }

    @Override
    public List<TrainerSpecialityCommand> getTrainerSpecialities() {
        return trainerSpecialityRepository
                .findAll()
                .stream()
                .map(ts -> {
                    return trainerSpecialityMapper.trainerSpecialityToTrainerSpecialityCommand(ts);
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainerSpecialityCommand getTrainerSpecialityByID(Long id) {
        return trainerSpecialityMapper.trainerSpecialityToTrainerSpecialityCommand(trainerSpecialityRepository.getById(id));
    }
}
