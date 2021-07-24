package com.springfirst.solutions.gym.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gym extends AbstractEntity{

    @Column(name = "gym_name")
    private String name;
    @Column(name = "about")
    private String gymInfo;

    @OneToOne
    private Address address;

    @OneToMany
    @Singular
    private Set<Trainer> trainers;
}
