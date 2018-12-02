package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 01.12.2018
 */
@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    private Set<EmployerDocument> employerDocuments = new HashSet<>();

    public Document() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<EmployerDocument> getEmployerDocuments() {
        return employerDocuments;
    }
    public void setEmployerDocuments(Set<EmployerDocument> employerDocuments) {
        this.employerDocuments = employerDocuments;
    }
}
