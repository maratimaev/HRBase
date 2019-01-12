package ru.bellintegrator.hrbase.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Отображение данных документа
 */
public class DocumentView {
    /**
     * id документа
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String id;

    /**
     * Номер документа
     */
    @NotEmpty
    @Size(max = 20)
    private String number;

    /**
     * Код типа документа
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String typeId;

    /**
     * Дата документа
     */
    @NotEmpty
    @Size(max = 10)
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DocumentView{"
                + "id='" + id + '\''
                + ", number='" + number + '\''
                + ", typeId='" + typeId + '\''
                + ", date='" + date + '\''
                + '}';
    }
}
