package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя найти организацию по Id в БД
 */
public class CantFindById extends RuntimeException {
    /**
     * Id организации
     */
    private String id;

    public CantFindById(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
