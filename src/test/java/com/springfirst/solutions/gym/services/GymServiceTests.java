package com.springfirst.solutions.gym.services;

import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.mappers.GymMapper;
import com.springfirst.solutions.gym.repositories.GymRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class GymServiceTests {

    @Mock
    private GymRepository gymRepository;

    private GymService gymService;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        gymService =new GymServiceImpl(GymMapper.INSTANCE, gymRepository);
    }
    @Test
    public void getGymDetails_success(){

        // TODO Address when we can nest the Mapper
        final String info = "This is the gym";
        final String name = "Bob's Sick Swan shop";

        Gym gym = Gym.builder().gymInfo(info).name(name).build();
        when(gymRepository.findFirstByGymInfoNotNull()).thenReturn(Optional.of(gym));

        GymCommand gymCommand=gymService.getGym();
        Assertions.assertAll(
                () -> {Assertions.assertNotNull(gymCommand);},
                () -> {Assertions.assertEquals(gymCommand.getGymInfo(), info);},
                () -> {Assertions.assertEquals(gymCommand.getName(), name);}
        );
    }


}
