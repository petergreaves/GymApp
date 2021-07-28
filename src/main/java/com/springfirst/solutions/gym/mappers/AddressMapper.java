package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.domain.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper( AddressMapper.class);
    AddressCommand addressToAddressCommand(Address a);

}
