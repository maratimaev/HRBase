package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View для office
 */
public class OfficeView {
    /**
     * id офиса
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Update.class})
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b", groups = {InputProfile.Update.class})
    private String id;

    /**
     * Имя офиса
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Update.class})
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String name;

    /**
     * Адрес
     */
    @JsonView(OutputProfile.Full.class)
    @NotEmpty(groups = {InputProfile.Update.class})
    @Size(max = 200, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String address;

    /**
     * Телефон
     */
    @JsonView(OutputProfile.Full.class)
    @Size(max = 20, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(OutputProfile.Short.class)
    @Pattern(regexp = "false|true+$|^$", groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String isActive;

    @NotEmpty(groups = {InputProfile.GetList.class, InputProfile.Save.class})
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b", groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
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

    @Override
    public String toString() {
        return "OfficeView{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", address='" + address + '\''
                + ", phone='" + phone + '\''
                + ", isActive='" + isActive + '\''
                + ", orgId='" + orgId + '\''
                + '}';
    }
}
