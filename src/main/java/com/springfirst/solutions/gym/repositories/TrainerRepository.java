package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Trainer;
import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long>{

    List<Trainer> findAllBySpecialities(TrainerSpeciality speciality);
}
