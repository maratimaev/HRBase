package ru.bellintegrator.hrbase.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.bellintegrator.hrbase.entity.Organization;

import java.util.List;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 01.12.2018
 */
public interface OrganizationRepository extends JpaRepository <Organization, Integer> {
    public List<Organization> findOrganizationsByIsActiveTrue();
    public List<Organization> findOrganizationsByName(String name);
}
