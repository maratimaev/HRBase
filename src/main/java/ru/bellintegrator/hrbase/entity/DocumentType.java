package ru.bellintegrator.hrbase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, связанная с таблицей document_type
 */
@Entity
@Table(name = "document_type")
public class DocumentType {
    /**
     * id типа документа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Код типа документа
     */
    @Column(length = 2, nullable = false)
    private String code;

    /**
     * Название типа документа
     */
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * Список документов одного типа, связанный с таблицей document
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
    private Set<Document> documents = new HashSet<>();

    public DocumentType() {
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
    public Set<Document> getDocuments() {
        return documents;
    }
    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
}
