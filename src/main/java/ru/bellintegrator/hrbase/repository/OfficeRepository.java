package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Office;

/**
 * Репозиторий методов для работы с БД
 */
public interface OfficeRepository extends JpaRepository<Office, Integer>, JpaSpecificationExecutor<Office> {
}
