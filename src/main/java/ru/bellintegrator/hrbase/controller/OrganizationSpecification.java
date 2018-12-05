package ru.bellintegrator.hrbase.controller;

import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.entity.Organization;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 04.12.2018
 */
public class OrganizationSpecification {

    public Specification<Organization> organizationList(String name, String inn, String isActive) {
        return new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(name != null && !name.isEmpty()) {
                    predicates.add(cb.equal(root.get("name"), name));
                }
                if(Integer.parseInt(inn) != 0) {
                    predicates.add(cb.equal(root.get("inn"), inn));
                }
                if(Boolean.parseBoolean(isActive)) {
                    predicates.add(cb.equal(root.get("isActive"), isActive));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }


}
