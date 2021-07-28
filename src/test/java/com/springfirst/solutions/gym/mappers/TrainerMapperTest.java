package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
//@ContextConfiguration(classes = {
//        TrainerMapperImpl.class,
//        TrainerSpecialityMapperImpl.class})
public class TrainerMapperTest {

    @Autowired
    ApplicationContext applicationContext ;

    @Configuration
    public static class Config {

        @Bean
        public TrainerSpecialityMapper trainerSpecialityMapper() {
            //return new TrainerSpecialityMapperImpl();
            return Mappers.getMapper(TrainerSpecialityMapper.class);
        }

        @Bean
        public TrainerMapper trainerMapper() {
            return Mappers.getMapper(TrainerMapper.class);
        }
    }


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
        TrainerCommand trainerCommand = TrainerMapper.INSTANCE.trainerToTrainerCommand(trainer);

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

//    static class Config {
//
//        @Bean
//        TrainerSpecialityMapper trainerSpecialityMapper() {
//
//            return TrainerSpecialityMapper.INSTANCE;
//        }
//    }
}

