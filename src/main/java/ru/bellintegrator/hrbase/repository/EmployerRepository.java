package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Employer;

/**
 * Репозиторий методов для работы с БД
 */
public interface EmployerRepository extends JpaRepository<Employer, Integer>, JpaSpecificationExecutor<Employer> {
}
