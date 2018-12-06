package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Organization;

/**
 * Репозиторий методов для работы с БД
 */
public interface OrganizationRepository extends JpaRepository <Organization, Integer>, JpaSpecificationExecutor<Organization> {
}
