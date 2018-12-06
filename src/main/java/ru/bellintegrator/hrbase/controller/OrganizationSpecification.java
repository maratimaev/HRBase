package ru.bellintegrator.hrbase.controller;

import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.Exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.entity.Organization;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizationSpecification {

    public static Specification<Organization> OrganizationBy(String id) {
        return new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                int i;
                try {
                    i = Integer.parseInt(id);
                } catch (Exception ex) {
                    throw new ThereIsNoSuchOrganization();
                }
                return cb.equal(root.get("id"), i);
            }
        };
    }

    public static Specification<Organization> listBy(String name, String inn, String isActive) {
        return new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(name == null) {
                    throw new ThereIsNoSuchOrganization();
                }
                predicates.add(cb.equal(root.get("name"), name));
                try {
                    if(inn != null) {
                        predicates.add(cb.equal(root.get("inn"), Integer.parseInt(inn)));
                    }
                }catch (Exception e) {
                    throw new ThereIsNoSuchOrganization();
                }
                if(isActive != null) {
                    if(!(isActive.equalsIgnoreCase("true") || isActive.equalsIgnoreCase("false")))
                    {
                        throw new ThereIsNoSuchOrganization();
                    }
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
