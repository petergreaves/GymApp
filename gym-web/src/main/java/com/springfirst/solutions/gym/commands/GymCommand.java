package com.springfirst.solutions.gym.commands;

import com.springfirst.solutions.gym.domain.Address;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class GymCommand {

    private String name;
    private String gymInfo;
    private AddressCommand address;
}
