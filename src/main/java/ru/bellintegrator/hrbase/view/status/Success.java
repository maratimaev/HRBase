package ru.bellintegrator.hrbase.view.status;

/**
 * Возвращение сообщения об успешности операции
 */
public class Success implements Result {
    private String result;

    public Success() {
        this.result = "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
