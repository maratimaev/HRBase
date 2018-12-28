package ru.bellintegrator.hrbase.view.employer;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * In View для employer
 */
public class EmployerViewUpdate extends EmployerView {
    /**
     * id сотрудника
     */
    @NotEmpty
    @Pattern(regexp = "\\b(?!(?:0)\\b)\\d{1,9}\\b")
    private String id;

    /**
     * Имя
     */
    @NotEmpty
    @Size(max = 50)
    private String firstName;

    /**
     * Второе имя
     */
    @Size(max = 50)
    private String secondName;

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
     * Признак идентификации
     */
    @Pattern(regexp = "false|true+$|^$")
    private String isIdentified;

    @Pattern(regexp = "\\d{0,3}")
    private String citizenshipCode;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getSecondName() {
        return secondName;
    }

    @Override
    public void setSecondName(String secondName) {
        this.secondName = secondName;
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
    public String getIsIdentified() {
        return isIdentified;
    }

    @Override
    public void setIsIdentified(String isIdentified) {
        this.isIdentified = isIdentified;
    }

    @Override
    public String getCitizenshipCode() {
        return citizenshipCode;
    }

    @Override
    public void setCitizenshipCode(String citizenshipCode) {
        this.citizenshipCode = citizenshipCode;
    }
}
