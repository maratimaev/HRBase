package ru.bellintegrator.hrbase.view;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Отображение даанных организации
 */
public class OrganizationViewUpdate extends OrganizationView {

    /**
     * id организации
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String id;

    /**
     * Имя организации
     */
    @NotEmpty
    @Size(max = 50)
    private String name;

    /**
     * Полное имя организации
     */
    @NotEmpty
    @Size(max = 100)
    private String fullName;

    /**
     * Инн организации
     */
    @NotEmpty
    @Size(max = 12)
    private String inn;

    /**
     * КПП организации
     */
    @NotEmpty
    @Size(max = 9)
    private String kpp;

    /**
     * Адрес
     */
    @NotEmpty
    @Size(max = 200)
    private String address;

    /**
     * Телефон
     */
    @Size(max = 20)
    private String phone;

    /**
     * Признак активности
     */
    @Pattern(regexp = "false|true")
    private String isActive;

    @Override
    public String getIsActive() {
        return isActive;
    }

    @Override
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String getInn() {
        return inn;
    }

    @Override
    public void setInn(String inn) {
        this.inn = inn;
    }

    @Override
    public String getKpp() {
        return kpp;
    }

    @Override
    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
