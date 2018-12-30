package ru.bellintegrator.hrbase.view.employer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * In View для employer
 */
public class EmployerViewList extends EmployerView {

    /**
     * Имя
     */
    @Size(max = 50)
    private String firstName;

    /**
     * Фамилия
     */
    @Size(max = 50)
    private String lastName;

    /**
     * Отчество
     */
    @Size(max = 50)
    private String middleName;

    /**
     * Должность
     */
    @Size(max = 50)
    private String position;

    /**
     * Код документа
     */
    @Pattern(regexp = "\\d{0,2}")
    private String docCode;

    /**
     * Код страны
     */
    @Pattern(regexp = "\\d{0,3}")
    private String citizenshipCode;

    /**
     * Оффис сотрудника
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String officeId;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getPosition() {
        return position;
    }

    @Override
    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String getDocCode() {
        return docCode;
    }

    @Override
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    @Override
    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    @Override
    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    @Override
    public String getOfficeId() {
        return officeId;
    }

    @Override
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    @Override
    public String toString() {
        return "EmployerViewList{"
                + "firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", middleName='" + middleName + '\''
                + ", position='" + position + '\''
                + ", docCode='" + docCode + '\''
                + ", citizenshipCode='" + citizenshipCode + '\''
                + ", officeId='" + officeId + '\''
                + '}';
    }
}
