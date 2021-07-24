package com.springfirst.solutions.gym.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name = "trainer")
public class Trainer extends AbstractPerson{

    @Column(name = "employee_number")
    private String employeeNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trainer_specialities", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name="speciality_id"))
    @Singular
    private Set<TrainerSpeciality> specialties = new HashSet<>();
}
