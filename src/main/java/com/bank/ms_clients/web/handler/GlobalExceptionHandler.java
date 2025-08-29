package com.bank.ms_clients.web.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.ms_clients.application.dto.ErrorDTO;
import com.bank.ms_clients.application.exception.DataNotFound;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<ErrorDTO> dataNotFoundExceptionHandling(DataNotFound ex,
                                                                  ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getPath().value()   // antes: request.getDescription(false)
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> globalExceptionHandling(Exception ex,
                                                            ServerHttpRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getPath().value()
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex,
                                                                       ServerHttpRequest request) {
        String details = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDTO(
                        LocalDateTime.now(),
                        "Validación fallida",
                        details
                ));
    }
}
