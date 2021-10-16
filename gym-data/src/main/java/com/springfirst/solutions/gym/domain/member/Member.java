package com.springfirst.solutions.gym.domain.member;

import com.springfirst.solutions.gym.domain.AbstractPerson;
import com.springfirst.solutions.gym.domain.Visit;
import com.springfirst.solutions.gym.domain.security.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member extends AbstractPerson {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_membership", joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name="membership_id"))
    @Singular
    private Set<Membership> memberships;

    private String memberID;

    private LocalDate dateOfBirth;
    private String trainingGoals;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_visit", joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name="visit_id"))
    @Singular
    private Set<Visit> visits;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
}
