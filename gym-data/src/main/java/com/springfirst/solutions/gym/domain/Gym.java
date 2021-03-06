package com.springfirst.solutions.gym.domain;

import com.springfirst.solutions.gym.domain.trainer.Trainer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Gym extends AbstractEntity{

    @Column(name = "gym_name")
    private String name;
    @Column(name = "about")
    private String gymInfo;

    @OneToOne
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular
    private Set<Trainer> trainers;
}
