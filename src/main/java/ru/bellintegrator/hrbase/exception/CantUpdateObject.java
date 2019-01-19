package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя обновить поля объекта
 */
public class CantUpdateObject extends RuntimeException {
    private String obj;

    private Exception ex;

    public CantUpdateObject() {
    }

    public CantUpdateObject(String obj, Exception ex) {
        this.obj = obj;
        this.ex = ex;
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

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }
}
