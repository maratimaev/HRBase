package ru.bellintegrator.hrbase.exception;

/**
 * Выбрасывается, если нельзя найти организацию по имени, инн, признаку активности. Все параметры обязательные.
 */
public class CantFindByNamePhoneActive extends RuntimeException {
    /**
     * Id организации
     */
    private String orgId;

    /**
     * Имя офиса
     */
    private String name;
    /**
     * Телефон офиса
     */
    private String phone;
    /**
     * Признак активности
     */
    private String isActive;

    public CantFindByNamePhoneActive() {
    }

    public CantFindByNamePhoneActive(String orgId, String name, String phone, String isActive) {
        this.name = name;
        this.phone = phone;
        this.isActive = isActive;
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
