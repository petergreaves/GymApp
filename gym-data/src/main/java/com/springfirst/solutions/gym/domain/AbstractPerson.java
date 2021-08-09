package com.springfirst.solutions.gym.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Lob;
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

    private String telNo;
    private String name;

    @Lob
    private Byte[] image;
}
