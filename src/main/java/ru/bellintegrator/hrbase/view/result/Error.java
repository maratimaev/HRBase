package ru.bellintegrator.hrbase.view.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import ru.bellintegrator.hrbase.view.profile.WrapperProfile;

/**
 * Возвращение сообщения об ошибке в виде json
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error implements Result {
    @JsonView(WrapperProfile.Data.class)
    private String error;

    private HttpStatus httpStatus;

    public Error() {
    }

    public Error(String error) {
        this.error = error;
    }

    public Error(String error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
