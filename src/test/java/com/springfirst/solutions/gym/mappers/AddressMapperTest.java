package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {


    @Test
    public void toCommand(){

        Address address = Address.builder()
                .buildingIdentifier("97")
                .street("Berry Rd")
                .county("Surrey")
                .postcode("TW17 0FF")
                .build();
        AddressCommand addressCommand = AddressMapper.INSTANCE.addressToAddressCommand(address);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(address.getBuildingIdentifier(), addressCommand.getBuildingIdentifier());},
                () -> {Assertions.assertEquals(address.getStreet(), addressCommand.getStreet());},
                () -> {Assertions.assertEquals(address.getCounty(), addressCommand.getCounty());},
                () -> {Assertions.assertEquals(address.getPostcode(), addressCommand.getPostcode());}
        );
    }
}