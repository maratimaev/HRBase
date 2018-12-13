package ru.bellintegrator.hrbase.service.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.entity.Organization;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Спецификации для создания запросов к БД
 */
public class OrganizationSpecification {
    private static final Logger log = LoggerFactory.getLogger(OrganizationSpecification.class.getName());

    /** Поиск организации по id
     * @param id организации
     * @return условие выборки
     */
    public static Specification<Organization> organizationBy(String id) {
        return new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                int i;
                try {
                    log.info(String.format("Try to find organizations by id=%s", id));
                    i = Integer.parseInt(id);
                } catch (Exception e) {
                    log.error(String.format("Error parsing %s to int \n Exception: %s", id, e));
                    throw new ThereIsNoSuchOrganization();
                }
                return cb.equal(root.get("id"), i);
            }
        };
    }

    /** Поиск организаций по параметрам
     * @param name имя
     * @param inn ИНН
     * @param isActive признак активности
     * @return условие выборки
     */
    public static Specification<Organization> listBy(String name, String inn, String isActive) {
        return new Specification<Organization>() {
            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (name == null) {
                    log.error(String.format("There is no field to find organization name:%s", name));
                    throw new ThereIsNoSuchOrganization();
                }
                predicates.add(cb.equal(root.get("name"), name));
                try {
                    if (inn != null) {
                        predicates.add(cb.equal(root.get("inn"), Integer.parseInt(inn)));
                    }
                } catch (Exception e) {
                    log.error(String.format("Error parsing %s to int \n Exception: %s", inn, e));
                    throw new ThereIsNoSuchOrganization();
                }
                if (isActive != null) {
                    if (!(isActive.equalsIgnoreCase("true") || isActive.equalsIgnoreCase("false"))) {
                        log.error(String.format("There is wrong field to find organization isActive:%s", isActive));
                        throw new ThereIsNoSuchOrganization();
                    }
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                log.info(String.format("Set specification to find organization"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
