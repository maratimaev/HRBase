package ru.bellintegrator.hrbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * Сущность, связанная с таблицей document
 */
@Entity
@Table(name = "document")
public class Document {
    /**
     * id документа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Тип документа
     */
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private DocumentType documentType;

    /**
     * Номер документа
     */
    @Column(length = 20, nullable = false)
    private String number;

    /**
     * Дата выдачи документа
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
