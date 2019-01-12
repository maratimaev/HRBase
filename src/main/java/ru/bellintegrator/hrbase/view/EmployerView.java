package ru.bellintegrator.hrbase.view;

import com.fasterxml.jackson.annotation.JsonView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * View для employer
 */
public class EmployerView {
    /**
     * id сотрудника
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Update.class})
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b", groups = {InputProfile.Update.class})
    private String id;

    /**
     * Id офиса сотрудника
     */
    @NotEmpty(groups = {InputProfile.GetList.class})
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b", groups = {InputProfile.GetList.class, InputProfile.Save.class})
    private String officeId;

    /**
     * Имя
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String firstName;

    /**
     * Фамилия
     */
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class})
    private String lastName;

    /**
     * Отчество
     */
    @JsonView(OutputProfile.Short.class)
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String middleName;

    /**
     * Второе имя
     */
    @JsonView(OutputProfile.Short.class)
    @Size(max = 50, groups = {InputProfile.Update.class})
    private String secondName;

    /**
     * Должность
     */
    @JsonView(OutputProfile.Short.class)
    @NotEmpty(groups = {InputProfile.Save.class, InputProfile.Update.class})
    @Size(max = 50, groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String position;

    /**
     * Телефон
     */
    @JsonView(OutputProfile.Full.class)
    @Size(max = 50, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String phone;

    /**
     * Код типа документа
     */
    @Pattern(regexp = "\\d{0,2}", groups = {InputProfile.GetList.class, InputProfile.Save.class})
    private String docCode;

    /**
     * Название документа
     */
    @JsonView(OutputProfile.Full.class)
    @Size(max = 100, groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String docName;

    /**
     * Номер документа
     */
    @JsonView(OutputProfile.Full.class)
    @Pattern(regexp = "\\d{0,20}", groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String docNumber;

    /**
     * Дата документа
     */
    @JsonView(OutputProfile.Full.class)
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", groups = {InputProfile.Save.class, InputProfile.Update.class})
    private String docDate;

    /**
     * Гржаданство
     */
    @JsonView(OutputProfile.Full.class)
    private String citizenshipName;

    /**
     * Код страны
     */
    @JsonView(OutputProfile.Full.class)
    @Pattern(regexp = "\\d{0,3}", groups = {InputProfile.GetList.class, InputProfile.Save.class, InputProfile.Update.class})
    private String citizenshipCode;

    /**
     * Признак идентификации
     */
    @JsonView(OutputProfile.Full.class)
    @Pattern(regexp = "false|true+$|^$", groups = {InputProfile.Save.class, InputProfile.Update.class})
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
        return "EmployerView{"
                + "id='" + id + '\''
                + ", officeId='" + officeId + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", middleName='" + middleName + '\''
                + ", secondName='" + secondName + '\''
                + ", position='" + position + '\''
                + ", phone='" + phone + '\''
                + ", docCode='" + docCode + '\''
                + ", docName='" + docName + '\''
                + ", docNumber='" + docNumber + '\''
                + ", docDate='" + docDate + '\''
                + ", citizenshipName='" + citizenshipName + '\''
                + ", citizenshipCode='" + citizenshipCode + '\''
                + ", isIdentified='" + isIdentified + '\''
                + '}';
    }
}
