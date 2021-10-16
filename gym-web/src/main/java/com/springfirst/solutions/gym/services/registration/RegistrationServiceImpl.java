package com.springfirst.solutions.gym.services.registration;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import com.springfirst.solutions.gym.domain.registration.Stage;
import com.springfirst.solutions.gym.exceptions.NoSuchRegistrationException;
import com.springfirst.solutions.gym.mappers.registration.RegistrationMapper;
import com.springfirst.solutions.gym.repositories.registration.RegistrationRepository;


import java.util.Optional;

public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, RegistrationMapper registrationMapper) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public Optional<RegistrationState> findRegistrationByEmail(String email) {

        return findRegState(email);
    }

    @Override
    public RegistrationStateCommand setRegistrationState(RegistrationStateCommand regCommand, Stage stage) {

        Optional<RegistrationState> state = findRegState(regCommand.getEmail());

        if (state.isPresent()){
            RegistrationState updated= registrationRepository
                    .save(registrationMapper.registrationStateCommandToRegistrationState(regCommand));
            return registrationMapper.registrationStateToRegistrationStateCommand(updated);
        }
        else{
            throw new
                    NoSuchRegistrationException("No such registration with email : " +regCommand.getEmail());
        }

    }

    @Override
    public void deleteRegistration(RegistrationStateCommand regCommand) {

        final String email = regCommand.getEmail();

        Optional<RegistrationState> state = findRegState(regCommand.getEmail());

        if (state.isPresent()){
            registrationRepository.delete(registrationMapper.registrationStateCommandToRegistrationState(regCommand));
        }
        else{
            throw new
                    NoSuchRegistrationException("No such registration with email : " +email);
        }


    }


    private Optional<RegistrationState> findRegState(String email){

        Optional<RegistrationState> state = registrationRepository.findRegistrationStateByEmail(email);

        if (state.isPresent()){
            return state;
        }
        else{
            throw new
                    NoSuchRegistrationException("No such registration with email : " +email);
        }

    }
}
