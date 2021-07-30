package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class TrainerServiceTests {

    @Mock
    private TrainerRepository trainerRepository;
    private TrainerService trainerService;

    private Trainer t1;
    private Trainer t2;
    private  final String t1TelNo="82348 923939";
    private  final String t2TelNo="038589 948498";
    private final String t1EmpID = "AB01";
    private final String t1Name="Billy Budd";


    @Autowired
    private TrainerMapper trainerMapper;

    @BeforeEach
    public void setup() {

        t1 = Trainer.builder()
                .employeeNumber(t1EmpID)
                .name(t1Name)
                .telNo(t1TelNo)
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("yoga")
                       .build())
                .build();

        t2 = Trainer.builder()
                .employeeNumber("DD01")
                .name("Mary Muscles")
                .telNo(t2TelNo)
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("classes")
                       .build())
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("pilates")
                       .build())
                .build();

        trainerService = new TrainerServiceImpl(trainerRepository, trainerMapper);
    }


    @Test
    public void getTrainerByEmployeeID_success(){

       when(trainerRepository.findByEmployeeNumber(anyString())).thenReturn(Optional.of(t1));
       TrainerCommand t = trainerService.getTrainerByEmployeeID(t1EmpID);

       Assertions.assertEquals(t.getName(), t1Name);

    }

    @Test
    public void getAllTrainers_success() {

        when(trainerRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        List<TrainerCommand> trainerCommandList = trainerService.getAllTrainers();

        Assertions.assertAll(
                () -> {
                    Assertions.assertEquals(trainerCommandList.size(), 2);
                },
                () -> {
                    Assertions.assertEquals(trainerCommandList.get(0).getName(), "Billy Budd");
                },
                () -> {
                    Assertions.assertEquals(trainerCommandList.get(1).getName(), "Mary Muscles");
                },
                () -> {
                    Assertions.assertEquals(trainerCommandList.get(0).getTelNo(), t1TelNo);
                },
                () -> {
                    Assertions.assertEquals(trainerCommandList.get(1).getTelNo(), t2TelNo);
                }
        );


    }

}
