package com.devsuperior.bds02.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsuperior.bds02.services.exceptions.CityNotFoundException;
import com.devsuperior.bds02.services.exceptions.EventInUseException;
import com.devsuperior.bds02.services.exceptions.EventNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ CityNotFoundException.class, EventNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleNotFound(RuntimeException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(EventInUseException.class)
    public ResponseEntity<ErrorResponse> handleResourceInUse(RuntimeException exception, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception, request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus httpStatus, Exception exception, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                httpStatus.value(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(body);
    }

    public record ErrorResponse(Instant timestamp, int status, String message, String path) {
    }

}
