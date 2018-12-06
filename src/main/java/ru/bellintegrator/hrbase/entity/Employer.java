package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;

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
     * Офис сотрудника
     */
    @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;

    /**
     * Имя
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Фамилия
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "middle_name")
    private String middleName;

    /**
     * Второе имя
     */
    @Column(name = "second_name")
    private String secondName;

    /**
     * Должность
     */
    @Column(nullable = false)
    private String position;

    /**
     * Телефон
     */
    private String phone;

    /**
     * Удостоверение личности
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

    public Employer() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
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
    public boolean isIdentified() {
        return isIdentified;
    }
    public void setIdentified(boolean identified) {
        isIdentified = identified;
    }
    public Document getDocument() {
        return document;
    }
    public void setDocument(Document document) {
        this.document = document;
    }
    public Country getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(Country citizenship) {
        this.citizenship = citizenship;
    }

}
