package com.springfirst.solutions.gym.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private String errorCode;
    private String message;
    private String detail;

}
