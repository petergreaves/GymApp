package com.springfirst.solutions.gym.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
public class Gym extends AbstractEntity{

    @Column(name = "gym_name")
    private String name;
    @Column(name = "about")
    private String gymInfo;
}
