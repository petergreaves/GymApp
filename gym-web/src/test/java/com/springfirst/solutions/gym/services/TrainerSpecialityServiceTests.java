package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.mappers.TrainerSpecialityMapper;
import com.springfirst.solutions.gym.repositories.TrainerSpecialityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class TrainerSpecialityServiceTests {

    @Mock
    private TrainerSpecialityRepository trainerSpecialityRepository;


    private TrainerSpecialityMapper trainerSpecialityMapper;
    private TrainerSpecialityService  trainerSpecialityService;

    private List<TrainerSpeciality> specs;

    @BeforeEach
    public void setup(){
        trainerSpecialityMapper = TrainerSpecialityMapper.INSTANCE;
        trainerSpecialityService = new TrainerSpecialityServiceImpl(trainerSpecialityRepository, trainerSpecialityMapper);

        specs = new ArrayList<>();

        specs.add(TrainerSpeciality.builder().description("Pilates").build());
        specs.add(TrainerSpeciality.builder().description("Yoga").build());
        specs.add(TrainerSpeciality.builder().description("Strength").build());
        specs.add(TrainerSpeciality.builder().description("Core").build());

    }


    @Test
    public void getTrainerSpecialities_success(){

        when(trainerSpecialityRepository.findAll()).thenReturn(specs);
        List<TrainerSpecialityCommand> fromService = trainerSpecialityService.getTrainerSpecialities();

        Assertions.assertAll(
                () -> {
                    Assertions.assertNotNull(fromService);
                },
                () -> {
                    Assertions.assertEquals(fromService.size(), specs.size());
                }
        );

    }
}
