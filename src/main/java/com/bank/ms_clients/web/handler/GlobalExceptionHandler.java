package com.bank.ms_clients.web.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.bank.ms_clients.application.dto.ErrorDTO;
import com.bank.ms_clients.application.exception.DataNotFound;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<?> dataNotFoundExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDTO(
                        LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false)),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandling(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new ErrorDTO(
                        LocalDateTime.now(),
                        exception.getMessage(),
                        request.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(
                new ErrorDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        ex.getConstraintViolations()
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .toList()
                                .toString()),
                HttpStatus.BAD_REQUEST);
    }

}
