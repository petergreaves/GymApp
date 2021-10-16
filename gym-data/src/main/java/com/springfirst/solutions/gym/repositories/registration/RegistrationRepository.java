package com.springfirst.solutions.gym.repositories.registration;

import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<RegistrationState, Long> {

    Optional<RegistrationState> findRegistrationStateByEmail(String email);
}
