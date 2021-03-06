package com.springfirst.solutions.gym.commands;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerCommand {

    @NotBlank
    @Size(min = 3, max = 32)
    private String name;

    @NotBlank
    @Size(min = 7, max = 12)
    private String telNo;

    @NotBlank
    @Size(min=5, max=5)
    private String employeeID;

    @NotBlank
    @Size(min = 20, max = 255)
    private String biography;

    private Boolean isNew;
    private Long id;

    private Byte[] image;

    private Boolean imagePresent;

    @Singular
    private Set<Long> trainerSpecialityCommandIDs;
}
