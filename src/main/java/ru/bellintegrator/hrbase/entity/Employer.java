package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 30.11.2018
 */
@Entity
@Table(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "office_id")
    private Office office;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "second_name")
    private String secondName;

    @Column(nullable = false)
    private String position;

    private String phone;

    @OneToOne
    @JoinColumn(name = "document_id")
    private EmployerDocument employerDocument;

    @OneToOne
    @JoinColumn(name = "citizenship_id")
    private Country citizenship;

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
    public EmployerDocument getEmployerDocument() {
        return employerDocument;
    }
    public void setEmployerDocument(EmployerDocument employerDocument) {
        this.employerDocument = employerDocument;
    }
    public Country getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(Country citizenship) {
        this.citizenship = citizenship;
    }

}
