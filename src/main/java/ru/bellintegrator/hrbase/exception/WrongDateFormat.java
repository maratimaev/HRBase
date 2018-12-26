package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если неправильный формат даты
 */
public class WrongDateFormat extends RuntimeException {
    /**
     * Дата
     */
    private String date;

    public WrongDateFormat() {
    }

    public WrongDateFormat(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String daet) {
        this.date = date;
    }
}
