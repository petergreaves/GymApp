package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.TrainerSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerSpecialityRepository extends JpaRepository<TrainerSpeciality, Long> {

    List<TrainerSpeciality> findAllByDescriptionIgnoringCase(String desc);
}
