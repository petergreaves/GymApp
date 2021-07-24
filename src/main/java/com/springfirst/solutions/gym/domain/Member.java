package com.springfirst.solutions.gym.domain;

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
@Table(name = "member")
public class Member extends AbstractPerson{

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "member_membership", joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name="membership_id"))
    @Singular
    private Set<Membership> memberships;

    private String memberID;
}
