package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
@Slf4j
public class TrainerMapperTest {

    @Autowired
    ApplicationContext applicationContext ;

    @Autowired
    TrainerMapper trainerMapper;

    @Test
    public void testMapperBeans(){

        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(log::info);

        Assertions.assertAll(

                () -> {
                    Assertions.assertNotNull(applicationContext.getBean("trainerMapper"));
                },
                () -> {
                    Assertions.assertNotNull(applicationContext.getBean("trainerSpecialityMapper"));
                }
        );
    }

    @Test
    public void toCommand() {

        Trainer trainer = Trainer.builder()
                .employeeNumber("A997")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpeciality(TrainerSpeciality.builder().description("classes").build())
                .trainerSpeciality(TrainerSpeciality.builder().description("yoga").build())
                .build();
        TrainerCommand trainerCommand = trainerMapper.trainerToTrainerCommand(trainer);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(trainer.getName(), trainerCommand.getName());
                },
                () -> {
                    Assertions.assertEquals(trainer.getTelNo(), trainerCommand.getTelNo());
                }
                , () -> {
                    Assertions.assertEquals(trainer.getTrainerSpecialities().size(), trainerCommand.getTrainerSpecialityCommands().size());
                }
        );
    }

}

