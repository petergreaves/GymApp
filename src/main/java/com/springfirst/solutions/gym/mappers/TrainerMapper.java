package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Address;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(uses = {TrainerSpecialityMapper.class})
public interface TrainerMapper {

    TrainerMapper INSTANCE = Mappers.getMapper( TrainerMapper.class);

    @Mapping(source = "trainerSpecialities", target = "trainerSpecialityCommands")
    TrainerCommand trainerToTrainerCommand(Trainer t);

}
