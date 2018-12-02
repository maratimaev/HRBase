package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 01.12.2018
 */
@Entity
@Table(name = "employer_document")
public class EmployerDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Document document;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Date date;

    public EmployerDocument() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Document getDocument() {
        return document;
    }
    public void setDocument(Document document) {
        this.document = document;
    }
}
