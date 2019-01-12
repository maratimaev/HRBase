package ru.bellintegrator.hrbase.view.result;

/**
 * Возвращение сообщения об успешности операции
 */
public class Success {
    private String result;

    public Success() {
        this.result = "success";
    }

    public String getResult() {
        return result;
    }
}
