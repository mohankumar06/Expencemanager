package com.springboot.expencemanager.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Exception handling
 *
 */
@ControllerAdvice
public class CustomExceptions extends RuntimeException {
    // Any general exception
    private Logger logger = LoggerFactory.getLogger(CustomExceptions.class);

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleUserNotFoundException(RecordNotFoundException ex, WebRequest req) {

        logger.error("Record Not Found Exception ");
        List<String> errors = new ArrayList<String>();
        errors.add(ex.getLocalizedMessage());

        ErrorMessage error = new ErrorMessage("Record Not Found", new Date(), errors);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest web) {
        List<String> errors = new ArrayList<String>();
        errors.add(ex.getLocalizedMessage());
        ErrorMessage error = new ErrorMessage("Server Error", new Date(), errors);
        return new ResponseEntity<Object>(errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}