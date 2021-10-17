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
   private String name;
   private String password;
   private LocalDate created;
   private LocalDate updated;
   private String email;

   @Transient
   @Builder.Default
   private Boolean isNew=false;

}
