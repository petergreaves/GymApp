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

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "biography")
    private String biography;
    private String imagePath;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "trainer_trainer_speciality", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name="speciality_id"))
    @Singular
    private Set<TrainerSpeciality> trainerSpecialities;
}
