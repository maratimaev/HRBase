package ru.bellintegrator.hrbase.view.employer;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.profile.Profile;

/**
 * Out View для employer
 */
public class EmployerView {
    /**
     * id сотрудника
     */
    @JsonView(Profile.Short.class)
    private String id;


    /**
     * Id офиса сотрудника
     */
    private String officeId;

    /**
     * Имя
     */
    @JsonView(Profile.Short.class)
    private String firstName;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Отчество
     */
    @JsonView(Profile.Short.class)
    private String middleName;

    /**
     * Второе имя
     */
    @JsonView(Profile.Short.class)
    private String secondName;

    /**
     * Должность
     */
    @JsonView(Profile.Short.class)
    private String position;

    /**
     * Телефон
     */
    @JsonView(Profile.Full.class)
    private String phone;

    /**
     * Код типа документа
     */
    private String docCode;

    /**
     * Название документа
     */
    @JsonView(Profile.Full.class)
    private String docName;

    /**
     * Номер документа
     */
    @JsonView(Profile.Full.class)
    private String docNumber;

    /**
     * Дата документа
     */
    @JsonView(Profile.Full.class)
    private String docDate;

    /**
     * Гржаданство
     */
    @JsonView(Profile.Full.class)
    private String citizenshipName;

    /**
     * Код страны
     */
    @JsonView(Profile.Full.class)
    private String citizenshipCode;

    /**
     * Признак идентификации
     */
    @JsonView(Profile.Full.class)
    private String isIdentified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDocCode() {
        return docCode;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getCitizenshipName() {
        return citizenshipName;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public String getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(String isIdentified) {
        this.isIdentified = isIdentified;
    }

    @Override
    public String toString() {
        return "EmployerView{" +
                "id='" + id + '\'' +
                ", officeId='" + officeId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", position='" + position + '\'' +
                ", phone='" + phone + '\'' +
                ", docCode='" + docCode + '\'' +
                ", docName='" + docName + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", docDate='" + docDate + '\'' +
                ", citizenshipName='" + citizenshipName + '\'' +
                ", citizenshipCode='" + citizenshipCode + '\'' +
                ", isIdentified='" + isIdentified + '\'' +
                '}';
    }
}
