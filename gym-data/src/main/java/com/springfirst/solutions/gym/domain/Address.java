package com.springfirst.solutions.gym.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address extends AbstractEntity{

    @Column(name = "building_identifier")
    private String buildingIdentifier;
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "post_code")
    private String postcode;
    @Column(name = "county")
    private String county;


}
