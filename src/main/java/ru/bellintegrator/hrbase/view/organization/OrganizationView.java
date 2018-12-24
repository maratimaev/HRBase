package ru.bellintegrator.hrbase.view.organization;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.Profile;

/**
 * Отображение даанных организации
 */
public class OrganizationView {
    /**
     * id организации
     */
    @JsonView(Profile.Short.class)
    private String id;

    /**
     * Имя организации
     */
    @JsonView(Profile.Short.class)
    private String name;

    /**
     * Полное имя организации
     */
    @JsonView(Profile.Full.class)
    private String fullName;

    /**
     * Инн организации
     */
    @JsonView(Profile.Full.class)
    private String inn;

    /**
     * КПП организации
     */
    @JsonView(Profile.Full.class)
    private String kpp;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
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
}
