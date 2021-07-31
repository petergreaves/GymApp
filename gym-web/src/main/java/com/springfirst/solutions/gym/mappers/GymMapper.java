package com.springfirst.solutions.gym.mappers;


import com.springfirst.solutions.gym.commands.GymCommand;
import com.springfirst.solutions.gym.domain.Gym;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {AddressMapper.class})
public interface GymMapper {

    GymMapper INSTANCE = Mappers.getMapper( GymMapper.class);
    GymCommand gymToGymCommand(Gym g);

}
