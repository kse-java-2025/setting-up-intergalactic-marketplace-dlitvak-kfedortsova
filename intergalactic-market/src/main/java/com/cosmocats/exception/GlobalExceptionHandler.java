package com.cosmocats.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String objectName = ex.getBindingResult().getObjectName();

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("Field '%s' %s",
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        String fullMessage = String.format("Validation failed for object '%s': %s",
                objectName,
                message);

        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                fullMessage, request.getRequestURI());

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handlePathErrors(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {

        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad request",
                ex.getMessage(), request.getRequestURI());

        return ResponseEntity.badRequest().body(body);
    }
}
