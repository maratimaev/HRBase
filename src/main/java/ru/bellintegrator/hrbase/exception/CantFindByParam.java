package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя найти объект по параметрам в БД
 */
public class CantFindByParam extends RuntimeException {
    /**
     * параметр
     */
    private String param;

    private RuntimeException ex;

    public CantFindByParam() {
    }

    public CantFindByParam(String param) {
        this.param = param;
    }

    public CantFindByParam(String param, RuntimeException ex) {
        this.param = param;
        this.ex = ex;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public RuntimeException getEx() {
        return ex;
    }

    public void setEx(RuntimeException ex) {
        this.ex = ex;
    }
}
