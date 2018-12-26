package ru.bellintegrator.hrbase.view.organization;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Отображение даанных организации
 */
public class OrganizationViewList extends OrganizationView {

    /**
     * Имя организации
     */
    @NotEmpty
    @Size(max = 50)
    private String name;

    /**
     * Инн организации
     */
    @Size(max = 12)
    private String inn;

    /**
     * Признак активности
     */
    @Pattern(regexp = "false|true+$|^$")
    private String isActive;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public String getIsActive() {
        return isActive;
    }

    @Override
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
