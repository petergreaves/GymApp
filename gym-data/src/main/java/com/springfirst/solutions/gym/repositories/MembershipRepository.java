package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
