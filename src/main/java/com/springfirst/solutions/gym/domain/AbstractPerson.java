package com.springfirst.solutions.gym.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractPerson extends AbstractEntity{

    private String telNo;
    private String name;
}
