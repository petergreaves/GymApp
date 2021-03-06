package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.member.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipTypeRepository extends JpaRepository<MembershipType, Long> {
}
