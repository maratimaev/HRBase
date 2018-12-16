package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.WrapperProfile;

import java.util.ArrayList;
import java.util.List;

/** Класс - обертка для выводимых данных
 * @param <T>
 */
public class Wrapper<T> {
    @JsonView(WrapperProfile.Data.class)
    private List<T> data;

    public Wrapper() {
    }

    public Wrapper(List<T> data) {
        this.data = data;
    }

    /** Конструктор с доавлением элемента в список
     * @param t
     */
    public Wrapper(T t) {
        data = new ArrayList<>();
        data.add(t);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
