package com.springfirst.solutions.gym.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainer_speciality")
public class TrainerSpeciality extends AbstractEntity{

    @Column(name ="description")
    private String description;
}
