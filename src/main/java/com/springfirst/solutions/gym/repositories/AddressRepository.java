package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
