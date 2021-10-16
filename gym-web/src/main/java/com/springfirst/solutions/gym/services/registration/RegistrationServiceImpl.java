package com.springfirst.solutions.gym.services.registration;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.exceptions.NoSuchRegistrationException;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;

import java.util.Optional;

public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Optional<RegistrationState> findRegistrationByEmail(String email) {

        Optional<RegistrationState> state = registrationRepository.findRegistrationStateByEmail(email);

        if (state.isPresent()){
            return state;
        }
        else{
            throw new
                NoSuchRegistrationException("No such registration with email : " +email);
        }
    }

    @Override
    public void setRegistrationState(RegistrationStateCommand state, Stage stage) {

    }

    @Override
    public void deleteRegistration(RegistrationStateCommand regCommand) {

    }
}
