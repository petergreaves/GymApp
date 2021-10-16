package com.springfirst.solutions.gym.exceptions;

public class NoSuchRegistrationException extends RuntimeException{
    public NoSuchRegistrationException(String message) {
        super(message);
    }
}
