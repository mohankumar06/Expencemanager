package com.springboot.expencemanager.exceptions;

import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ErrorMessage {
    private Date timestamp;
    private List<String> details;
    private HttpStatus httpStatus;
    private String message;

    public ErrorMessage(String message, Date timestamp, List<String> details) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
