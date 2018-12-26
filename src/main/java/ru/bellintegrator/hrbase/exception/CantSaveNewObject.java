package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя сохранить новый объект в БД
 */
public class CantSaveNewObject extends RuntimeException {
    private String obj;

    public CantSaveNewObject() {
    }

    public CantSaveNewObject(String obj) {
        this.obj = obj;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
