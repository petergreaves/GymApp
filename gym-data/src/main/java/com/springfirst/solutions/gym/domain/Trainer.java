package com.springfirst.solutions.gym.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trainer")
public class Trainer extends AbstractPerson{

    @Column(name = "employee_number", unique=true)
    private String employeeID;

    @Column(name = "biography")
    private String biography;

    @Column(name = "image_path")
    private String imagePath;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trainer_trainer_speciality", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name="speciality_id"))
    @Singular
    private Set<TrainerSpeciality> trainerSpecialities;
}
