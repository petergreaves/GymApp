package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Address;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TrainerSpecialityMapperTest {

    @Test
    public void toCommand(){

        TrainerSpeciality trainerSpeciality = TrainerSpeciality.builder()
                .description("classes")
                .build();
        TrainerSpecialityCommand tsCommand = TrainerSpecialityMapper.INSTANCE.trainerSpecialityToTrainerSpecialityCommand(trainerSpeciality);
        Assertions.assertAll(

                () -> {Assertions.assertEquals(trainerSpeciality.getDescription(), tsCommand.getDescription());}

        );
    }
}
