package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GymRepository extends JpaRepository<Gym, Long> {

    Optional<Gym> findFirstByGymInfoNotNull();
}
