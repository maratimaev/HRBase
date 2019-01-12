package ru.bellintegrator.hrbase.view.result;

/**
 * Возвращение сообщения об ошибке в виде json
 */
public class Error {
    private String error;

    public Error() {
    }

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
