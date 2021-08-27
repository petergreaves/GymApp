package com.springfirst.solutions.gym.domain.security;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "authorities")
    private Set<Role> roles;

    private String permission;
}
