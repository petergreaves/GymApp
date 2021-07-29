package com.springfirst.solutions.gym.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="visit")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visit extends AbstractEntity{

    @Column(name = "visit_dt")
    private LocalDateTime visitDateTime;
    @Column(name = "purpose")
    private String purposeOfVisit;
}
