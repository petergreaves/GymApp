package com.springfirst.solutions.gym.converters;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class TrainerToTrainerCommand implements Converter<Trainer, TrainerCommand> {

    @Autowired
    TrainerSpecialityToTrainerSpecialityCommand converter;

    @Override
    public TrainerCommand convert(Trainer trainer) {

        TrainerCommand.TrainerCommandBuilder builder = TrainerCommand.builder();

        builder.name(trainer.getName());
        trainer.getSpecialities()
                .stream()
                .forEach(s -> builder.trainerSpecialityCommand(converter.convert(s)));
        return builder.build();
    }
}
