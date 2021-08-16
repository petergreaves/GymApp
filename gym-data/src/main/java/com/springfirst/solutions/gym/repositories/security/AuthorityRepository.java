package com.springfirst.solutions.gym.repositories.security;

import com.springfirst.solutions.gym.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
