package com.springfirst.solutions.gym.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class TrainerInvalidContentException extends RuntimeException{

    private List<ObjectError> errorList;
    public TrainerInvalidContentException(String message, List<ObjectError> errorList) {
                super(message);
                this.errorList = errorList;
    }

    public TrainerInvalidContentException(String message) {
        super(message);
    }
    public List<ObjectError> getErrorList(){ return  errorList;}
}
