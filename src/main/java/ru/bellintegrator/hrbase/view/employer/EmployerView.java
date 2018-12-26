package ru.bellintegrator.hrbase.view.employer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.Office;
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
     * Оффис сотрудника
     */
    private Office office;

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
     * Документ, удостоверяющий личность
     */
    private Document document;

    private String docCode;

    private String docName;

    private String docNumber;

    private String docDate;

    /**
     * Гражданство
     */
    private Country citizenship;

    private String citizenshipName;

    private String citizenshipCode;

    /**
     * Признак идентификации
     */
    private String isIdentified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
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

    public String getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(String isIdentified) {
        this.isIdentified = isIdentified;
    }

    public Country getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Country citizenship) {
        this.citizenship = citizenship;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @JsonView(Profile.Full.class)
    @JsonProperty("docName")
    public String getDocName() {
        String result = null;
        if (this.document != null) {
            result = this.document.getDocumentType().getName();
        }
        return result;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    @JsonView(Profile.Full.class)
    @JsonProperty("docNumber")
    public String getDocNumber() {
        String result = null;
        if (this.document != null) {
            result = this.document.getNumber();
        }
        return result;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    @JsonView(Profile.Full.class)
    @JsonProperty("docDate")
    public String getDocDate() {
        String result = null;
        if (this.document != null) {
            result = this.document.getDate().toString();
        }
        return result;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    @JsonView(Profile.Full.class)
    @JsonProperty("citizenshipName")
    public String getCitizenshipName() {
        String result = null;
        if (this.citizenship != null) {
            result = this.citizenship.getName();
        }
        return result;
    }

    public void setCitizenshipName(String citizenshipName) {
        this.citizenshipName = citizenshipName;
    }

    @JsonView(Profile.Full.class)
    @JsonProperty("citizenshipCode")
    public String getCitizenshipCode() {
        String result = null;
        if (this.citizenship != null) {
            result = this.citizenship.getCode();
        }
        return result;
    }

    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getDocCode() {
        String result = null;
        if (this.document != null) {
            result = this.document.getDocumentType().getCode();
        }
        return result;
    }

    public void setDocCode(String docCode) {
        this.docCode = docCode;
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
