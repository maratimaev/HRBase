package ru.bellintegrator.hrbase.view.office;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Сущность, связанная с таблицей Office
 */
public class OfficeViewUpdate extends OfficeView {

    /**
     * id офиса
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String id;

    /**
     * Имя офиса
     */
    @NotEmpty
    @Size(max = 50)
    private String name;

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
    @Pattern(regexp = "false|true+$|^$")
    private String isActive;

    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String orgId;

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

    @Override
    public String getIsActive() {
        return isActive;
    }

    @Override
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
