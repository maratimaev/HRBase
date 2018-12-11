package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.OrganizationProfile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Отображение даанных организации
 */
public class OrganizationView {
    /**
     * id организации
     */
    @JsonView(OrganizationProfile.Short.class)
    private int id;

    /**
     * Имя организации
     */
    @JsonView(OrganizationProfile.Short.class)
    @NotEmpty
    @Size(max = 50)
    private String name;

    /**
     * Полное имя организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @NotEmpty
    @Size(max = 100)
    private String fullName;

    /**
     * Инн организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @NotEmpty
    @Size(max = 12)
    private String inn;

    /**
     * КПП организации
     */
    @JsonView(OrganizationProfile.Full.class)
    @NotEmpty
    @Size(max = 9)
    private String kpp;

    /**
     * Адрес
     */
    @JsonView(OrganizationProfile.Full.class)
    @NotEmpty
    @Size(max = 200)
    private String address;

    /**
     * Телефон
     */
    @JsonView(OrganizationProfile.Full.class)
    @Size(max = 20)
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(OrganizationProfile.Short.class)
    private boolean isActive = false;

    public int getId() {
        return id;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
