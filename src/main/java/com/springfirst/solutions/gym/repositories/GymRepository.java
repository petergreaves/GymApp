package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Long> {
}
