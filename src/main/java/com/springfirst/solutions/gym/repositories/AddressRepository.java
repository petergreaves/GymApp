package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.county = :c")
    Optional<Address> findByCounty(@Param("c") String county);

    @Query("SELECT a FROM Address a WHERE a.county = :c")
    StreetOnly findStreetByCounty(@Param("c") String county);

}
