package com.springfirst.solutions.gym.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class TrainerDuplicateEmployeeIDException extends RuntimeException{

    private List<ObjectError> errorList;
    public TrainerDuplicateEmployeeIDException(String message) {
        super(message);
    }
    public List<ObjectError> getErrorList(){ return  errorList;}
    public void setErrorList(List<ObjectError> errors){

        this.errorList = errors;
    }
}
