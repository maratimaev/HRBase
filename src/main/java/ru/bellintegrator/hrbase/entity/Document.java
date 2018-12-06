package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Документ сотрудника
 */
@Entity
@Table(name = "document")
public class Document {
    /**
     * Id документа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Тип документа
     */
    @ManyToOne
    @JoinColumn(name = "type_id")
    private DocumentType documentType;

    /**
     * Номер документа
     */
    @Column(nullable = false)
    private String number;

    /**
     * Дата выдачи
     */
    @Column(nullable = false)
    private Date date;

    public Document() {
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

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
