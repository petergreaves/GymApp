package com.springfirst.solutions.gym.controllers.advice;

import com.springfirst.solutions.gym.controllers.rest.TrainerRestController;
import com.springfirst.solutions.gym.exceptions.TrainerInvalidContentException;
import com.springfirst.solutions.gym.exceptions.TrainerNotFoundException;
import com.springfirst.solutions.gym.error.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class TrainerRestControllerExceptionHandler {

@ExceptionHandler(TrainerInvalidContentException.class)
@ResponseBody
protected ResponseEntity<Error> handleInvalidContent(Exception ex) {

    log.error("Handling Trainer bad content exception");
    log.error(ex.getMessage());

    Error error = Error.builder()
            .errorCode("EGA-002")
            .message(ex.getMessage())
            .detail("Invalid content in request to create/update trainer")
            .build();

    return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity(error,HttpStatus.NOT_FOUND);
    }

}
