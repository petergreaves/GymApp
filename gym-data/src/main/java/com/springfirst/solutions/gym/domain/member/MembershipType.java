package com.springfirst.solutions.gym.domain.member;

import com.springfirst.solutions.gym.domain.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "membershiptype")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipType extends AbstractEntity {

    private String type;
    private String description;
}
