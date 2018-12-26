package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя найти объект по параметрам в БД
 */
public class CantFindByParam extends RuntimeException {
    /**
     * параметр
     */
    private String param;

    public CantFindByParam() {
    }

    public CantFindByParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
