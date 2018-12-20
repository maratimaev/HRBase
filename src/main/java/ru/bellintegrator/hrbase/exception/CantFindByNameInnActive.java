package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя найти организацию по имени, инн, признаку активности. Все параметры обязательные.
 */
public class CantFindByNameInnActive extends RuntimeException {
    /**
     * Имя организации
     */
    private String name;
    /**
     * ИНН организации
     */
    private String inn;
    /**
     * Признак активности
     */
    private String isActive;

    public CantFindByNameInnActive(String name, String inn, String isActive) {
        this.name = name;
        this.inn = inn;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
