package com.springfirst.solutions.gym.domain.trainer;

import com.springfirst.solutions.gym.domain.AbstractEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainer_speciality")
public class TrainerSpeciality extends AbstractEntity {

    @Column(name ="description")
    private String description;

    @Column(name ="name")
    private String name;
}
