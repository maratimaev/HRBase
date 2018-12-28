package ru.bellintegrator.hrbase.view.result;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.WrapperProfile;

/**
 * Возвращение сообщения об успешности операции
 */
public class Success implements Result {
    @JsonView(WrapperProfile.Data.class)
    private String result;

    public Success() {
        this.result = "success";
    }

    public String getResult() {
        return result;
    }
}
