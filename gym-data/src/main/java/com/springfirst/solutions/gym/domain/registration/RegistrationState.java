package com.springfirst.solutions.gym.domain.registration;


import com.springfirst.solutions.gym.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   private Stage stage;
   private LocalDate created;
   private LocalDate updated;
   private String email;

}
