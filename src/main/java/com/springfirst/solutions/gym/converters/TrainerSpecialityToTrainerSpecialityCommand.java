package com.springfirst.solutions.gym.converters;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.springframework.core.convert.converter.Converter;

public class TrainerSpecialityToTrainerSpecialityCommand implements Converter<TrainerSpeciality, TrainerSpecialityCommand> {

    @Override
    public TrainerSpecialityCommand convert(TrainerSpeciality ts) {
        return TrainerSpecialityCommand.builder().description(ts.getDescription()).build();
    }
}
