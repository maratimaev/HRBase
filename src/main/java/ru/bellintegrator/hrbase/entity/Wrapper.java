package ru.bellintegrator.hrbase.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.OutputProfile.OrganizationProfile;
import ru.bellintegrator.hrbase.OutputProfile.WrapperProfile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 04.12.2018
 */
public class Wrapper<T> {
    @JsonView(WrapperProfile.Data.class)
    private List<T> data;

    public Wrapper() {
    }

    public Wrapper(List<T> data) {
        this.data = data;
    }

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
