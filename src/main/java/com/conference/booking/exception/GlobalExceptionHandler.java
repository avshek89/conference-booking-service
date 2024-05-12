package com.conference.booking.exception;

import com.conference.booking.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

import static com.conference.booking.constant.ApiConstants.*;
import static com.conference.booking.util.Utils.buildErrorResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(DataAccessException e) {
        log.error("DataAccessException exception occurred {} ", e);
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(),TECHNICAL));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        log.error("Invalid Data exception {} ", e);
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(),TECHNICAL));
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(HttpMessageNotReadableException e) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(e.getMessage(),TECHNICAL));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<String>> handleProcessingExceptions(Exception e) {
        log.error("Generic exception occurred {} ", e);
        return ResponseEntity
                .ok()
                .body(buildErrorResponse(e.getMessage(),GENERIC));
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<Response<String>> handleCustomValidationExceptions(BookingException e) {
        log.error("Booking exception occurred {} ", e);
        return ResponseEntity
                .ok()
                .body(buildErrorResponse( e.getMessage(),FUNCTIONAL));
    }
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Response<String>> handleValidationExceptions(DateTimeParseException e) {
        return ResponseEntity
                .badRequest()
                .body(buildErrorResponse(TIME_FORMAT,TECHNICAL));
    }
}