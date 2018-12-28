package ru.bellintegrator.hrbase.exception;

import org.springframework.http.HttpStatus;

public class WrongInputField extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public WrongInputField() {
    }

    public WrongInputField(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
