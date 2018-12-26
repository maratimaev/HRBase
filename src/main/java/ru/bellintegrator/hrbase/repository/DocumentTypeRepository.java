package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.DocumentType;

/**
 * Репозиторий методов для работы с БД
 */
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer>, JpaSpecificationExecutor<DocumentType> {
    DocumentType findByCode(String code);
    DocumentType findByName(String name);
}
