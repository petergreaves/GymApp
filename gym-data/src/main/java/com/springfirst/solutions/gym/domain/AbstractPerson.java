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


    public AbstractPerson(Long id, String telNo, String name) {
        super(id);
        this.telNo = telNo;
        this.name = name;
    }

    protected String telNo;
    protected String name;
}