package ru.bellintegrator.hrbase.view.result;

/**
 * Возвращение сообщения об успешности операции
 */
public class Success implements Result {
    private String result;

    public Success() {
        this.result = "success";
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
