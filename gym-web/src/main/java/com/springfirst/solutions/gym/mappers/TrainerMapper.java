package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {TrainerSpecialityMapper.class})
public interface TrainerMapper {

    TrainerMapper INSTANCE = Mappers.getMapper( TrainerMapper.class);

    @Mapping(source = "trainerSpecialities", target = "trainerSpecialityCommands")
    TrainerCommand trainerToTrainerCommand(Trainer t);
    Trainer trainerCommandToTrainer(TrainerCommand t);

}
