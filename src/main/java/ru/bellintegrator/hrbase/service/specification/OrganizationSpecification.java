package ru.bellintegrator.hrbase.service.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.exception.CantFindByNameInnActive;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationSpecification.class.getName());

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
                    LOGGER.error(String.format("There is no field to find organization (name):%s", name));
                    throw new CantFindByNameInnActive(name, inn, isActive);
                }
                predicates.add(cb.equal(root.get("name"), name));
                if (inn == null) {
                    LOGGER.error(String.format("There is no field to find organization (inn):%s", inn));
                    throw new CantFindByNameInnActive(name, inn, isActive);
                }
                predicates.add(cb.equal(root.get("inn"), inn));

                if (isActive != null) {
                    if (!(isActive.equalsIgnoreCase("true") || isActive.equalsIgnoreCase("false"))) {
                        LOGGER.error(String.format("There is wrong field to find organization isActive:%s", isActive));
                        throw new CantFindByNameInnActive(name, inn, isActive);
                    }
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                LOGGER.debug(String.format("Set specification to find organization"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
