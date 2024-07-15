package com.crud_tutospring.demo.common;

import com.crud_tutospring.demo.modelEntity.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice//pour dire que cette classe est un controller advice, c'est-à-dire une classe qui va gérer les exceptions levées par les contrôleurs.
public class CustomExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)//pour dire que cette méthode doit être invoquée lorsqu'une exception de type PersonNotFoundException est levée.
    public ResponseEntity<ApiError> handlePersonNotFoundException(PersonNotFoundException error){
        ApiError apiError = new ApiError();
        apiError.setMessage(error.getMessage());
        apiError.setCode(HttpStatus.NOT_FOUND.value());
        apiError.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception error){
        ApiError apiError = new ApiError();
        apiError.setMessage(error.getMessage());
        apiError.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiError.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
