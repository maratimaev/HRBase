package ru.bellintegrator.hrbase.view.employer;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * In View для employer
 */
public class EmployerViewSave extends EmployerView {

    /**
     * Имя
     */
    @NotEmpty
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
    @NotEmpty
    @Size(max = 50)
    private String position;

    /**
     * Телефон
     */
    @Size(max = 50)
    private String phone;

    /**
     * Код документа
     */
    @Pattern(regexp = "\\d{0,2}")
    private String docCode;

    /**
     * Название документа
     */
    @Size(max = 100)
    private String docName;

    /**
     * Номер документа
     */
    @Pattern(regexp = "\\d{0,20}")
    private String docNumber;

    /**
     * Дата документа
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String docDate;

    /**
     * Код страны
     */
    @Pattern(regexp = "\\d{0,3}")
    private String citizenshipCode;

    /**
     * Признак идентификации
     */
    @Pattern(regexp = "false|true+$|^$")
    private String isIdentified;

    /**
     * Оффис сотрудника
     */
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
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    @Override
    public String getDocName() {
        return docName;
    }

    @Override
    public void setDocName(String docName) {
        this.docName = docName;
    }

    @Override
    public String getDocNumber() {
        return docNumber;
    }

    @Override
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    @Override
    public String getDocDate() {
        return docDate;
    }

    @Override
    public void setDocDate(String docDate) {
        this.docDate = docDate;
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
    public String getIsIdentified() {
        return isIdentified;
    }

    @Override
    public void setIsIdentified(String isIdentified) {
        this.isIdentified = isIdentified;
    }

    @Override
    public String getOfficeId() {
        return officeId;
    }

    @Override
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
}
