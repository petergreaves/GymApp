package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
