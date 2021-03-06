package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.trainer.TrainerSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TrainerSpecialityMapper {

    TrainerSpecialityMapper INSTANCE = Mappers.getMapper( TrainerSpecialityMapper.class);
    TrainerSpecialityCommand trainerSpecialityToTrainerSpecialityCommand(TrainerSpeciality ts);

}
