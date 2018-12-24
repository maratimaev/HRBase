package ru.bellintegrator.hrbase.view.office;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.Profile;

/**
 * Сущность, связанная с таблицей Office
 */
public class OfficeView {
    /**
     * id офиса
     */
    @JsonView(Profile.Short.class)
    private String id;

    /**
     * Имя офиса
     */
    @JsonView(Profile.Short.class)
    private String name;

    /**
     * Адрес
     */
    @JsonView(Profile.Full.class)
    private String address;

    /**
     * Телефон
     */
    @JsonView(Profile.Full.class)
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(Profile.Short.class)
    private String isActive;

    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
