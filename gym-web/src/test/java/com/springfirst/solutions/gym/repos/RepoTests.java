package com.springfirst.solutions.gym.repos;

import com.springfirst.solutions.gym.bootstrap.Dataloader;
import com.springfirst.solutions.gym.domain.Gym;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.domain.trainer.TrainerSpeciality;
import com.springfirst.solutions.gym.repositories.*;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;
import com.springfirst.solutions.gym.repositories.security.AuthorityRepository;
import com.springfirst.solutions.gym.repositories.security.RoleRepository;
import com.springfirst.solutions.gym.repositories.security.UserRepository;
import org.junit.jupiter.api.Assertions;
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

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RegistrationRepository registrationRepository;


    @BeforeEach
    public void setup() throws Exception {
        Dataloader dataloader = new Dataloader(
                trainerSpecialityRepository,
                trainerRepository,
                membershipRepository,
                membershipTypeRepository,
                gymRepository,
                addressRepository,
                memberRepository,
                visitRepository,
                registrationRepository,
                userRepository,
                authorityRepository,
                roleRepository);

        dataloader.run("Running...");
    }

    @Test
    public void checkGym_success(){

        List<Gym> gyms = gymRepository.findAll();
        Assertions.assertEquals(gyms.size(), 1);
    }

    @Test
    public void testTrainerSearchBySpec_success(){
        // add a trainer

        List<TrainerSpeciality> classes = trainerSpecialityRepository.findAllByNameIgnoringCase("yoga");
        List<Trainer> found = trainerRepository.findAllByTrainerSpecialities(classes.get(0));

        Assertions.assertTrue(found.size() > 0);
    }


}
