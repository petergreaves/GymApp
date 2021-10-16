package com.springfirst.solutions.gym.repositories;

import com.springfirst.solutions.gym.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member>  findByMemberID(String memberID);

}
