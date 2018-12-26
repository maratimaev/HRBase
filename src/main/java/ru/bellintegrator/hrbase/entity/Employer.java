package ru.bellintegrator.hrbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Сущность, связанная с таблицей employer
 */
@Entity
@Table(name = "employer")
public class Employer {
    /**
     * id сотрудника
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Оффис сотрудника
     */
    @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;

    /**
     * Имя
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "middle_name", length = 50)
    private String middleName;

    /**
     * Второе имя
     */
    @Column(name = "second_name", length = 50)
    private String secondName;

    /**
     * Должность
     */
    @Column(length = 50, nullable = false)
    private String position;

    /**
     * Телефон
     */
    @Column(length = 50)
    private String phone;

    /**
     * Документ, удостоверяющий личность
     */
    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;

    /**
     * Гражданство
     */
    @OneToOne
    @JoinColumn(name = "citizenship_id")
    private Country citizenship;

    /**
     * Признак идентификации
     */
    @Column(name = "is_identified")
    private boolean isIdentified = false;

    /**
     * Служебное полк JPA
     */
    @Version
    private Integer version;

    public int getId() {
        return id;
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

    public boolean getIsIdentified() {
        return isIdentified;
    }

    public void setIsIdentified(boolean isIdentified) {
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
}
