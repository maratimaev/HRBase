package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.DocumentType;

/**
 * Репозиторий методов для работы с БД
 */
public interface DocumentRepository extends JpaRepository<Document, Integer>, JpaSpecificationExecutor<Document> {
    Document findByNumber(String number);
    Document findByDocumentType(DocumentType documentType);
}
