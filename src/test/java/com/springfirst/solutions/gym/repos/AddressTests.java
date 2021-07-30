package com.springfirst.solutions.gym.repos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springfirst.solutions.gym.domain.Address;
import com.springfirst.solutions.gym.repositories.AddressRepository;
import org.aspectj.lang.annotation.Before;;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class AddressTests {


    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    private Long id;

    @BeforeEach
    public void before() {
        address = Address.builder()
                .county("Middx")
                .street("Beet St")
                .buildingIdentifier("99")
                        .build();
        address.setId(888L);
        addressRepository.save(address);
    }

    @AfterEach
    public void tearDown(){
        addressRepository.delete(address);

    }

    @Test
    public void findAddressQuery_success(){

        Optional<Address> address = addressRepository.findByCounty("Middx");
        Assertions.assertEquals(address.get().getBuildingIdentifier(), "99");

    }
    @Test
    public void findStreetForAddressQuery_success(){

        Optional<Address> address = addressRepository.findByStreet("Beet St");
        Assertions.assertEquals(address.get().getStreet(), "Beet St");
    }
}
