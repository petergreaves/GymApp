package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.commands.TrainerSpecialityCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.exceptions.TrainerDuplicateEmployeeIDException;
import com.springfirst.solutions.gym.exceptions.TrainerInvalidContentException;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import com.springfirst.solutions.gym.repositories.TrainerRepository;
import com.springfirst.solutions.gym.repositories.TrainerSpecialityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class TrainerServiceTests {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainerSpecialityRepository trainerSpecialityRepository;

    @Mock
    private GymRepository gymRepository;

    private TrainerService trainerService;

    @Mock
    private GymService gymService;

    private Trainer t1;
    private Trainer t2;
    private  final String t1TelNo="82348 923939";
    private  final String t2TelNo="038589 948498";
    private final String t1EmpID = "AB01";
    private final String t1Name="Billy Budd";


    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private GymMapper gymMapper;

    @BeforeEach
    public void setup() {

        t1 = Trainer.builder()
                .employeeID(t1EmpID)
                .name(t1Name)
                .telNo(t1TelNo)
                .id(1L)
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("yoga")
                       .build())
                .build();

        t2 = Trainer.builder()
                .employeeID("DD01")
                .name("Mary Muscles")
                .telNo(t2TelNo)
                .id(2L)
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("classes")
                       .build())
               .trainerSpeciality(TrainerSpeciality.builder()
                       .description("pilates")
                       .build())
                .build();

        trainerService = new TrainerServiceImpl(trainerRepository, gymService, trainerMapper,trainerSpecialityRepository);
       // gymService = new GymServiceImpl(gymMapper,trainerMapper,gymRepository);
    }


    @Test
    public void getTrainerByEmployeeID_success(){

       when(trainerRepository.findByEmployeeID(anyString())).thenReturn(Optional.of(t1));
       TrainerCommand t = trainerService.getTrainerByEmployeeID(t1EmpID);

       Assertions.assertEquals(t.getName(), t1Name);

    }

    @Test
    void testGetTrainer_fail() {

        when(trainerRepository.findByEmployeeID(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(TrainerNotFoundException.class, () -> {
            trainerService.getTrainerByEmployeeID("XXXX");
        });

    }

    @Test
    public void createTrainer_success(){

        Trainer savedTrainer  = Trainer.builder()
                .employeeID(t1EmpID)
                .name(t1Name)
                .telNo(t1TelNo)
                .id(999L)
                .trainerSpeciality(TrainerSpeciality.builder()
                        .description("yoga")
                        .id(55L)
                        .build())
                .build();

        TrainerCommand newTrainerCommand = TrainerCommand.builder()
                .employeeID(t1EmpID)
                .name(t1Name)
                .isNew(true)
                .telNo(t1TelNo)
                .trainerSpecialityCommandID(55L)
                .build();

        when(trainerRepository.save(any())).thenReturn(savedTrainer);

        TrainerCommand savedTrainerCommand = trainerService.createOrUpdateTrainer(newTrainerCommand);


        Assertions.assertAll(
                () -> {
                    Assertions.assertNotNull(savedTrainerCommand);
                },
                () -> {
                    Assertions.assertEquals(savedTrainerCommand.getName(), t1Name);
                },
                () -> {
                    Assertions.assertEquals(savedTrainerCommand.getTelNo(), t1TelNo);
                }
                ,
                () -> {
                    Assertions.assertEquals(savedTrainerCommand.getEmployeeID(), t1EmpID);
                }
        );

    }


    @Test
    public void deleteTrainerByEmployeeID_success(){

        String trainerEmployeeIDToDelete = "A001";
        Trainer toDel = Trainer.builder().build();

        when(trainerRepository.findByEmployeeID(any(String.class))).thenReturn(Optional.of(toDel));
        when(gymService.removeTrainer(any(TrainerCommand.class))).thenReturn(Set.of(TrainerCommand.builder().build()));
        trainerService.deleteTrainer(trainerEmployeeIDToDelete);

        verify(trainerRepository).delete(any(Trainer.class));
        verify(gymService,  times(1)).removeTrainer(any(TrainerCommand.class));
        verify(trainerRepository,  times(1)).findByEmployeeID(anyString());
    }

    @Test
    void testDeleteTrainer_fail() {

        when(trainerRepository.findByEmployeeID(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(TrainerNotFoundException.class, () -> {
            trainerService.deleteTrainer("XXXX");
        });
        verify(trainerRepository, times(0)).delete(any(Trainer.class));
        verify(trainerRepository, times(1)).findByEmployeeID(anyString());

    }

    @Test
    void testCreateTrainerWithDuplicateEmpId_fail() {

        Trainer t1 = Trainer.builder().employeeID("ABC01").build();
        TrainerCommand t2 = TrainerCommand.builder().employeeID("ABC01").isNew(true).build();

        when(trainerRepository.findByEmployeeID(anyString())).thenReturn(Optional.of(t1));

        Assertions.assertThrows(TrainerDuplicateEmployeeIDException.class, () -> {
            trainerService.createOrUpdateTrainer(t2);
        });

     //   verify(trainerRepository, times(0)).save(Trainer.class);
        verify(trainerRepository, times(1)).findByEmployeeID(anyString());

    }



    @Test
    public void getTrainerByID_success(){

        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(t1));
        TrainerCommand t = trainerService.getTrainerById(1L);

        Assertions.assertEquals(t.getName(), t1Name);
        verify(trainerRepository,  times(1)).findById((anyLong()));

    }

    @Test
    public void getNewTrainerInstance_success(){


        TrainerCommand t = trainerService.getNewTrainerInstance();

        Assertions.assertEquals(t.getName(), null);
        Assertions.assertEquals(t.getIsNew(), true);
        verifyNoInteractions(trainerRepository);

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
