package ru.bellintegrator.hrbase.view.result;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;

/** Класс - обертка для выводимых данных
 * @param <T>
 */
public class Wrapper<T> {
    @JsonView(OutputProfile.Data.class)
    private T data;

    public Wrapper() {
    }

    public Wrapper(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
