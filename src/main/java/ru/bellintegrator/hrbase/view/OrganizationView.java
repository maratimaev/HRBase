package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.OrganizationProfile;

/**
 * Отображение даанных организации
 */
public class OrganizationView {
    /**
     * id организации
     */
    @JsonView(OrganizationProfile.Short.class)
    private String id;

    /**
     * Имя организации
     */
    @JsonView(OrganizationProfile.Short.class)
    private String name;

    /**
     * Полное имя организации
     */
    @JsonView(OrganizationProfile.Full.class)
    private String fullName;

    /**
     * Инн организации
     */
    @JsonView(OrganizationProfile.Full.class)
    private String inn;

    /**
     * КПП организации
     */
    @JsonView(OrganizationProfile.Full.class)
    private String kpp;

    /**
     * Адрес
     */
    @JsonView(OrganizationProfile.Full.class)
    private String address;

    /**
     * Телефон
     */
    @JsonView(OrganizationProfile.Full.class)
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(OrganizationProfile.Short.class)
    private String isActive;

    public OrganizationView() {
    }

    public OrganizationView(String id, String name, String fullName, String inn, String kpp, String address) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.address = address;
    }

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
