package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
