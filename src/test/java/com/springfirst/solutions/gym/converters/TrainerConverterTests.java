package com.springfirst.solutions.gym.converters;


import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class TrainerConverterTests {

    @Autowired
    TrainerToTrainerCommand trainerToTrainerCommand;

    @Test
    public void toCommand(){

        Trainer trainer = Trainer.builder().speciality(TrainerSpeciality.builder().description("classes").build()).build();
        TrainerCommand trainerCommand =trainerToTrainerCommand.convert(trainer);

        Set<TrainerSpecialityCommand> tsCommands = trainerCommand.getTrainerSpecialityCommands();

        Assertions.assertTrue(tsCommands.size() == 1);
    }

    @TestConfiguration
    static class ConverterConfig {

        @Bean
        public TrainerToTrainerCommand trainerToTrainerCommand() {
            return new TrainerToTrainerCommand();
        }

        @Bean
        public TrainerSpecialityToTrainerSpecialityCommand trainerSpecialityToTrainerSpecialityCommand() {
            return new TrainerSpecialityToTrainerSpecialityCommand();
        }
    }

}
