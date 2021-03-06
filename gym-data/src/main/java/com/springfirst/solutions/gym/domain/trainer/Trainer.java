package com.springfirst.solutions.gym.domain.trainer;

import com.springfirst.solutions.gym.domain.AbstractPerson;
import com.springfirst.solutions.gym.domain.security.User;
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
public class Trainer extends AbstractPerson {

    @Column(name = "employee_number", unique=true)
    private String employeeID;

    @Column(name = "biography")
    private String biography;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trainer_trainer_speciality", joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name="speciality_id"))
    @Singular
    private Set<TrainerSpeciality> trainerSpecialities;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private User user;
}
