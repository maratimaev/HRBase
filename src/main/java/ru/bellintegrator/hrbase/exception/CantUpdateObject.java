package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя обновить поля объекта
 */
public class CantUpdateObject extends RuntimeException {
    private String obj;

    public CantUpdateObject() {
    }

    public CantUpdateObject(String obj) {
        this.obj = obj;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
