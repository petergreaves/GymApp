package com.springfirst.solutions.gym.domain;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "specialities")
public class TrainerSpeciality extends AbstractEntity{

    @Column(name ="description")
    private String description;
}
