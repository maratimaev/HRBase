package ru.bellintegrator.hrbase.view.result;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.OrganizationProfile;

/**
 * Возвращение сообщения об ошибке в виде json
 */
public class Error implements Result {
    @JsonView(OrganizationProfile.Short.class)
    private String error;

    public Error() {
    }

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
