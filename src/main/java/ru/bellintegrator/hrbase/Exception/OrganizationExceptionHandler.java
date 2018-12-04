package ru.bellintegrator.hrbase.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class OrganizationExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ThereIsNoSuchOrganization.class)
    public ResponseEntity<OrganizationException> handlerThereIsNoOrganization() {
        return new ResponseEntity<>(new OrganizationException("There is no such organization"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static class OrganizationException {
        private String error;

        public OrganizationException() {
        }

        public OrganizationException(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

}
