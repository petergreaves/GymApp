package com.springfirst.solutions.gym.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "membership")
public class Membership extends AbstractEntity{

    private LocalDate start;
    private LocalDate end;
    private Boolean active;

   @ManyToOne
   private MembershipType membershipType;

}
