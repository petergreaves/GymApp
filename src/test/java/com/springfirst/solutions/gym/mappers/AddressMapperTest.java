package com.springfirst.solutions.gym.mappers;

import com.springfirst.solutions.gym.commands.AddressCommand;
import com.springfirst.solutions.gym.configs.MapperConfigs;
import com.springfirst.solutions.gym.domain.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(classes = {MapperConfigs.class})
class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void toCommand(){

        Address address = Address.builder()
                .buildingIdentifier("97")
                .street("Berry Rd")
                .county("Surrey")
                .postcode("TW17 0FF")
                .build();
        AddressCommand addressCommand = addressMapper.addressToAddressCommand(address);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(address.getBuildingIdentifier(), addressCommand.getBuildingIdentifier());},
                () -> {Assertions.assertEquals(address.getStreet(), addressCommand.getStreet());},
                () -> {Assertions.assertEquals(address.getCounty(), addressCommand.getCounty());},
                () -> {Assertions.assertEquals(address.getPostcode(), addressCommand.getPostcode());}
        );
    }

    @Test
    public void toEntity(){

        AddressCommand addressCommand = AddressCommand.builder()
                .buildingIdentifier("97")
                .street("Berry Rd")
                .county("Surrey")
                .postcode("TW17 0FF")
                .build();
        Address address = AddressMapper.INSTANCE.addressCommandToAddress(addressCommand);

        Assertions.assertAll(

                () -> {Assertions.assertEquals(address.getBuildingIdentifier(), addressCommand.getBuildingIdentifier());},
                () -> {Assertions.assertEquals(address.getStreet(), addressCommand.getStreet());},
                () -> {Assertions.assertEquals(address.getCounty(), addressCommand.getCounty());},
                () -> {Assertions.assertEquals(address.getPostcode(), addressCommand.getPostcode());}
        );
    }
}