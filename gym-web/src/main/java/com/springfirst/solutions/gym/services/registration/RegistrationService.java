package com.springfirst.solutions.gym.services.registration;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;

import java.util.Optional;

public interface RegistrationService {

    Optional<RegistrationState> findRegistrationByEmail(String email);
    void setRegistrationState(RegistrationStateCommand stateCommand, Stage stage);
    void deleteRegistration(RegistrationStateCommand stateCommand);
}
