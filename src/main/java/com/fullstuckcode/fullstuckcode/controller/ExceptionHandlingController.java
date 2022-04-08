package com.fullstuckcode.fullstuckcode.controller;

import com.fullstuckcode.fullstuckcode.exception.NotFoundException;
import com.fullstuckcode.fullstuckcode.model.response.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleValidationException(ConstraintViolationException e) {
        WebResponse<String> webResponse = new WebResponse<>(
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                null);

        return new ResponseEntity<>(webResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        WebResponse<String> webResponse = new WebResponse<>(
                HttpStatus.NOT_FOUND.name(),
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null);

        return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
    }

}
