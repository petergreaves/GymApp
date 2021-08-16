package com.springfirst.solutions.gym.repositories.security;

import com.springfirst.solutions.gym.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
