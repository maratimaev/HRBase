package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если неправильный формат даты
 */
public class WrongDateFormat extends RuntimeException {
    /**
     * Дата
     */
    private String date;

    private Exception ex;

    public WrongDateFormat() {
    }

    public WrongDateFormat(String date) {
        this.date = date;
    }

    public WrongDateFormat(String date, Exception ex) {
        this.date = date;
        this.ex = ex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String daet) {
        this.date = date;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }
}
