package com.springfirst.solutions.gym.mappers.registration;

import com.springfirst.solutions.gym.commands.registration.RegistrationStateCommand;
import com.springfirst.solutions.gym.domain.registration.RegistrationState;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegistrationMapper {

    RegistrationMapper INSTANCE = Mappers.getMapper( RegistrationMapper.class);
    RegistrationStateCommand registrationStateToRegistrationStateCommand(RegistrationState state);
    RegistrationState registrationStateCommandToRegistrationState(RegistrationStateCommand stateCommand);

}
