package com.springfirst.solutions.gym.repos;

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

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    @BeforeEach
    public void before() {
        address = Address.builder().county("Middx").street("Beet St").buildingIdentifier("99").build();
        entityManager.persist(address);

    }

    @AfterEach
    public void tearDown(){
        entityManager.remove(address);

    }

    @Test
    public void findAddressQuery_success(){

        Optional<Address> address = addressRepository.findByCounty("Middx");
        Assertions.assertEquals(address.get().getBuildingIdentifier(), "99");
    }
//    @Test
//    public void findStreetForAddressQuery(){
//
//        StreetOnly street = addressRepository.findStreetByCountyIgnoringCase("rutland");
//        Assertions.assertEquals(street.getStreet(), "Swan St");
//    }
}
