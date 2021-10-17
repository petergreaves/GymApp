package com.springfirst.solutions.gym.services.registration;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.Stage;

import java.util.List;

public interface RegistrationService {

    RegistrationStateCommand findRegistrationByEmail(String email);
    RegistrationStateCommand setRegistrationState(RegistrationStateCommand stateCommand, Stage stage);
    void deleteRegistration(RegistrationStateCommand stateCommand);
    List<RegistrationStateCommand> getAllRegistrations();

    RegistrationStateCommand saveOrUpdateRegistration(RegistrationStateCommand stateCommand);


}
