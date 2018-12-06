package ru.bellintegrator.hrbase.repository;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.bellintegrator.hrbase.entity.Organization;

import java.util.List;

public interface OrganizationRepository extends JpaRepository <Organization, Integer>, JpaSpecificationExecutor<Organization> {

}
