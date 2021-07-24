package com.springfirst.solutions.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "membershiptype")
@NoArgsConstructor
@AllArgsConstructor
public class MembershipType extends AbstractEntity{

    private String description;
}
