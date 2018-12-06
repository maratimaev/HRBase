package ru.bellintegrator.hrbase.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Тип документа, удостоверяющего личность сотрудника
 */
@Entity
@Table(name = "document_type")
public class DocumentType {
    /**
     * id документа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Код документа
     */
    @Column(nullable = false)
    private String code;

    /**
     * Название
     */
    @Column(nullable = false)
    private String name;

    /**
     * Множество ссылающихся документов
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document_type")
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
