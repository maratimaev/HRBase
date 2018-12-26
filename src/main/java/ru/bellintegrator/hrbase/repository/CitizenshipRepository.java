package ru.bellintegrator.hrbase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Country;

/**
 * Репозиторий методов для работы с БД
 */
public interface CitizenshipRepository extends JpaRepository<Country, Integer>, JpaSpecificationExecutor<Country> {
    Country findByCode(String code);
}
