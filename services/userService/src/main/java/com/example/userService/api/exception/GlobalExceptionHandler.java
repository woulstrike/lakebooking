package com.example.userService.api.exception;

import com.example.userService.api.dto.ErrorResponse;
import com.example.userService.application.exceptions.LoginException;
import com.example.userService.application.exceptions.RegistrationException;
import com.example.userService.application.exceptions.UpdateException;
import com.example.userService.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ErrorResponse> handlerRegistration(RegistrationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), 400));
    }


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> handlerLogin(LoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ex.getMessage(), 401));
    }


    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<ErrorResponse> handlerUpdate(UpdateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), 400));
    }


    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handlerDomain(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), 400));
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage(), 404));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handlerException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ex.getMessage(), 500));
    }
}
