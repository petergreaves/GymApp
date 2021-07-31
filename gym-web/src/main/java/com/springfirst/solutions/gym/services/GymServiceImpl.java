package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import org.springframework.stereotype.Service;

@Service
public class GymServiceImpl implements GymService{

    private GymMapper gymMapper;
    private final GymRepository gymRepository;

    public GymServiceImpl(GymMapper gymMapper, GymRepository gymRepository) {
        this.gymMapper = gymMapper;
        this.gymRepository = gymRepository;
    }

    @Override
    public GymCommand getGym() {

        // TODO handle no gym by throwing exception

        Gym g = gymRepository.findFirstByGymInfoNotNull().get();
        return gymMapper.gymToGymCommand(g);
    }
}
