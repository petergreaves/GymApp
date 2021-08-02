package com.springfirst.solutions.gym.exceptions;

public class TrainerNotFoundException extends RuntimeException{
    public TrainerNotFoundException(String message) {
        super(message);
    }
}
