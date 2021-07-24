package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long>{
}
