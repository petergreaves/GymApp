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
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
@Slf4j
public class TrainerMapperTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TrainerMapper trainerMapper;

    @Test
    public void testMapperBeans() {

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
    public void toEntity() {

        TrainerCommand trainerCommand = TrainerCommand.builder()
                .employeeID("A997")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpecialityCommandID(1L)
                .trainerSpecialityCommandID(2L)
                .biography("Been a personal trainer for 10 years")
                .image(new Byte['3'])
                .build();
        Trainer trainer = trainerMapper.trainerCommandToTrainer(trainerCommand);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(trainer.getName(), trainerCommand.getName());
                },
                () -> {
                    Assertions.assertEquals(trainer.getTelNo(), trainerCommand.getTelNo());
                }
                , () -> {
                    Assertions.assertTrue(trainer.getTrainerSpecialities().size()==0);
                },
                () -> {
                    Assertions.assertEquals(trainer.getBiography(), trainerCommand.getBiography());
                },
                () -> {
                    Assertions.assertEquals(trainer.getId(), trainerCommand.getId());
                }
                ,() -> {
                    Assertions.assertEquals(trainer.getImage()[0], trainerCommand.getImage()[0]);
                }

        );
    }

    @Test
    public void toCommand() {

        Trainer trainer = Trainer.builder()
                .employeeID("A997")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpeciality(TrainerSpeciality.builder().description("classes").id(1L).build())
                .trainerSpeciality(TrainerSpeciality.builder().description("yoga").id(2L).build())
                .image(new Byte['4'])
                .biography("Been a personal trainer for 10 years")
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
                    Assertions.assertEquals(trainer.getTrainerSpecialities().size(),
                            trainerCommand.getTrainerSpecialityCommandIDs().size());
                }
                , () -> {
                    Assertions.assertTrue(trainerCommand.getTrainerSpecialityCommandIDs().containsAll(Set.of(1L, 2L)));

                },
                () -> {
                    Assertions.assertEquals(trainerCommand.getImagePresent(), true);
                },
                () -> {
                    Assertions.assertEquals(trainer.getBiography(), trainerCommand.getBiography());
                },
                () -> {
                    Assertions.assertEquals(trainer.getId(), trainerCommand.getId());
                }
                ,() -> {
                    Assertions.assertEquals(trainer.getImage()[0], trainerCommand.getImage()[0]);
                }
        );
    }

    @Test
    public void toCommandNoImage() {

        Trainer trainer = Trainer.builder()
                .employeeID("A997")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpeciality(TrainerSpeciality.builder().description("classes").id(1L).build())
                .trainerSpeciality(TrainerSpeciality.builder().description("yoga").id(2L).build())
                //.image(new Byte['4'])
                .biography("Been a personal trainer for 10 years")
                .build();
        TrainerCommand trainerCommand = trainerMapper.trainerToTrainerCommand(trainer);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(trainerCommand.getImagePresent(), false);
                }
        );
    }

    @Test
    public void toCommandNullImage() {

        Trainer trainer = Trainer.builder()
                .employeeID("A997")
                .name("Bill Bicep")
                .telNo("0129348 03993")
                .trainerSpeciality(TrainerSpeciality.builder().description("classes").id(1L).build())
                .trainerSpeciality(TrainerSpeciality.builder().description("yoga").id(2L).build())
                .image(null)
                .biography("Been a personal trainer for 10 years")
                .build();
        TrainerCommand trainerCommand = trainerMapper.trainerToTrainerCommand(trainer);

        Assertions.assertAll(

                () -> {
                    Assertions.assertEquals(trainerCommand.getImagePresent(), false);
                }
        );
    }

}

