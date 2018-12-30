package ru.bellintegrator.hrbase.view.doctype;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Отображение данных типа документа
 */
public class DocTypeViewSave {
    /**
     * id типа документа
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String id;

    /**
     * Имя типа документа
     */
    @NotEmpty
    @Size(max = 100)
    private String name;

    /**
     * Код типа документа
     */
    @NotEmpty
    @Pattern(regexp = "\\d{0,2}")
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "DocTypeViewSave{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", code='" + code + '\''
                + '}';
    }
}
