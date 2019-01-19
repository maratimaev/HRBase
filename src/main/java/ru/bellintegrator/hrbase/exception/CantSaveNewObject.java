package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя сохранить новый объект в БД
 */
public class CantSaveNewObject extends RuntimeException {
    private String obj;

    private Exception ex;

    public CantSaveNewObject() {
    }

    public CantSaveNewObject(String obj) {
        this.obj = obj;
    }

    public CantSaveNewObject(String obj, Exception ex) {
        this.obj = obj;
        this.ex = ex;
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

    public void setEx(RuntimeException ex) {
        this.ex = ex;
    }
}
