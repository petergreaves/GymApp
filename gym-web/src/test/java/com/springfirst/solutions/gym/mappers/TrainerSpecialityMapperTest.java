package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.trainer.TrainerSpeciality;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class TrainerSpecialityMapperTest {

    @Autowired
    private TrainerSpecialityMapper trainerSpecialityMapper;

    @Test
    public void toCommand() {

        TrainerSpeciality trainerSpeciality = TrainerSpeciality.builder()
                .name("Yoga")
                .description("Get bendy in Yoga!")
                .build();
        TrainerSpecialityCommand tsCommand = trainerSpecialityMapper.trainerSpecialityToTrainerSpecialityCommand(trainerSpeciality);
        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(trainerSpeciality.getDescription(), tsCommand.getDescription());
                },
                () -> {
                    Assertions.assertEquals(trainerSpeciality.getName(), tsCommand.getName());
                }
        );
    }
}
