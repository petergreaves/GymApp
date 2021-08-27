package com.springfirst.solutions.gym.repositories.security;

import com.springfirst.solutions.gym.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
