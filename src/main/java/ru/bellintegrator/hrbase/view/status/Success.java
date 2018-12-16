package ru.bellintegrator.hrbase.view.status;

/**
 * Возвращение сообщения об успешности операции
 */
public class Success implements Result {
    private String result = "success";

    public String getResult() {
        return result;
    }
}
