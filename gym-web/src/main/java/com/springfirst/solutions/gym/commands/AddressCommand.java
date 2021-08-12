package com.springfirst.solutions.gym.commands;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class AddressCommand {

    private String buildingIdentifier;
    private String street;
    private String city;
    private String postcode;
    private String county;
}
