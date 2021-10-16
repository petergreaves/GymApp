package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.trainer.Trainer;
import com.springfirst.solutions.gym.domain.trainer.TrainerSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long>{

    List<Trainer> findAllByTrainerSpecialities(TrainerSpeciality speciality);
    Optional<Trainer> findByEmployeeID(String employeeID);
}
