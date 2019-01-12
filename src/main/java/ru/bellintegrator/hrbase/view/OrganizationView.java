package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View для organization
 */
public class OrganizationView {
    /**
     * id организации
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Update.class})
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b", groups = {InputProfile.Update.class})
    private String id;
    
    /**
     * Имя организации
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String name;

    /**
     * Полное имя организации
     */
    @JsonView(OutputProfile.Full.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Size(max = 100, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String fullName;

    /**
     * Инн организации
     */
    @JsonView(OutputProfile.Full.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Pattern(regexp = "\\d{0,12}", groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String inn;

    /**
     * КПП организации
     */
    @JsonView(OutputProfile.Full.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Pattern(regexp = "\\d{0,9}", groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String kpp;

    /**
     * Адрес
     */
    @JsonView(OutputProfile.Full.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Size(max = 200, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String address;

    /**
     * Телефон
     */
    @JsonView(OutputProfile.Full.class)
    @Size(max = 20, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String phone;

    /**
     * Признак активности
     */
    @JsonView(OutputProfile.Short.class)
    @Pattern(regexp = "false|true+$|^$", groups = {InputProfile.Save.class, InputProfile.GetList.class, InputProfile.Update.class})
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

    @Override
    public String toString() {
        return "OrganizationView{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + ", fullName='" + fullName + '\''
                + ", inn='" + inn + '\''
                + ", kpp='" + kpp + '\''
                + ", address='" + address + '\''
                + ", phone='" + phone + '\''
                + ", isActive='" + isActive + '\''
                + '}';
    }
}
