package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.commands.TrainerCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Address;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.mappers.AddressMapper;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.mappers.TrainerMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
public class GymServiceTests {

    @Mock
    private GymRepository gymRepository;

    private GymService gymService;

    private Gym gym;
    private Address address;

    @Autowired
    private TrainerMapper trainerMapper;

    @Autowired
    private GymMapper gymMapper;

    @Autowired
    private AddressMapper addressMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        gymService = new GymServiceImpl(gymMapper, trainerMapper, addressMapper, gymRepository);
        final String info = "This is the gym";
        final String name = "Bob's Sick Swan shop";

        address= Address.builder()
                .buildingIdentifier("11")
                .street("Hamilton Rd")
                .county("Middx")
                .postcode("TT3 9JJ")
                .id(9L)
                .build();

        gym = Gym.builder().gymInfo(info).name(name).address(address).id(2L).build();
    }

    @Test
    public void getGymDetails_success() {

        // TODO Address when we can nest the Mapper

        when(gymRepository.findFirstByGymInfoNotNull()).thenReturn(Optional.of(gym));

        GymCommand gymCommand = gymService.getGym();
        Assertions.assertAll(
                () -> {
                    Assertions.assertNotNull(gymCommand);
                },
                () -> {
                    Assertions.assertNotNull(gymCommand.getAddress());
                },
                () -> {
                    Assertions.assertEquals(gymCommand.getGymInfo(), gym.getGymInfo());
                },
                () -> {
                    Assertions.assertEquals(gymCommand.getName(), gym.getName());
                }
        );
    }

    @Test
    public void removeTrainer_success() {

        // TODO Address when we can nest the Mapper
        final String info = "This is the gym";
        final String name = "Bob's Sick Swan shop";

        Gym gym = Gym.builder().gymInfo(info).name(name).build();

        gym.setTrainers(Set.of(Trainer.builder().employeeID("A123").build(),
                Trainer.builder().employeeID("B499").build(),
                Trainer.builder().employeeID("C7388").build()));

        when(gymRepository.findFirstByGymInfoNotNull()).thenReturn(Optional.of(gym));

        // lets' try to remove this one
        TrainerCommand toRemove = TrainerCommand.builder().employeeID("B499").build();

        Set<TrainerCommand> trainers = gymService.removeTrainer(TrainerCommand.builder().employeeID("B499").build());
        Assertions.assertTrue(trainers.stream().noneMatch(t -> t.getEmployeeID().equals(toRemove.getEmployeeID())));

    }


}
