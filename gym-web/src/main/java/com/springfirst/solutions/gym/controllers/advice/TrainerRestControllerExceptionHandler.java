package com.springfirst.solutions.gym.controllers.advice;

import com.springfirst.solutions.gym.error.Error;
import com.springfirst.solutions.gym.exceptions.TrainerDuplicateEmployeeIDException;
import com.springfirst.solutions.gym.exceptions.TrainerInvalidContentException;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class TrainerRestControllerExceptionHandler {


    @ExceptionHandler(TrainerDuplicateEmployeeIDException.class)
    @ResponseBody
    protected ResponseEntity<Error> handleDuplicateID(Exception ex) {

        log.error("Handling Duplicate Trainer ID exception");
        log.error(ex.getMessage());

        Error error = Error.builder()
                .errorCode("EGA-003")
                .message("A trainer with this employee ID already exists for this trainer.")
                .detail(
                        ex.getMessage())
                .build();

        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TrainerInvalidContentException.class)
    @ResponseBody
    protected ResponseEntity<Error> handleInvalidContent(TrainerInvalidContentException ex) {

        log.error("Handling Trainer bad content exception");
        log.error("Number of errors : " + ex.getErrorList().size());
        log.error(ex.getMessage());

        List<Error> errors = new ArrayList<>();

        ex.getErrorList()
                .stream()
                .forEach(er -> {
                    errors.add(Error.builder()
                            .errorCode("EGA-002")
                            .message(((FieldError) er).getField() + " failed validation.")
                            .detail(er.getDefaultMessage())
                            .build());
                });

        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrainerNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<Error> handleNotFound(Exception ex) {

        log.error("Handling Trainer not found exception");
        log.error(ex.getMessage());

        Error error = Error.builder()
                .errorCode("EGA-001")
                .message("An invalid or unknown trainer ID was specified in the request path")
                .detail(
                        ex.getMessage())
                .build();

        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

}
