package com.springfirst.solutions.gym.repos;

import com.springfirst.solutions.gym.bootstrap.Dataloader;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import com.springfirst.solutions.gym.repositories.*;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class RepoTests {

    @Autowired
    GymRepository gymRepository;

    @Autowired
    TrainerSpecialityRepository trainerSpecialityRepository;

    @Autowired
    TrainerRepository trainerRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    MembershipTypeRepository membershipTypeRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setup() throws Exception {
        Dataloader dataloader = new Dataloader(trainerSpecialityRepository, trainerRepository,
                membershipRepository, membershipTypeRepository, gymRepository, addressRepository, memberRepository);

        dataloader.run("Running...");
    }

    @Test
    public void checkGym(){

        List<Gym> gyms = gymRepository.findAll();
        Assertions.assertEquals(gyms.size(), 1);
    }

    @Test
    public void testTrainerSearchBySpec(){
        // add a trainer

        List<TrainerSpeciality> classes = trainerSpecialityRepository.findAllByDescriptionIgnoringCase("classes");
        List<Trainer> found = trainerRepository.findAllBySpecialities(classes.get(0));

        Assertions.assertTrue(found.size() > 0);
    }

}
