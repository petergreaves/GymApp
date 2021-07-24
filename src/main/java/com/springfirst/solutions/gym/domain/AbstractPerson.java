package com.springfirst.solutions.gym.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractPerson extends AbstractEntity{

    String telNo;
    protected String name;
}
