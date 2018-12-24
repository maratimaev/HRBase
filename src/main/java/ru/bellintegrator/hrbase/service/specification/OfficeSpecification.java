package ru.bellintegrator.hrbase.service.specification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.exception.CantFindById;
import ru.bellintegrator.hrbase.exception.CantFindByNamePhoneActive;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Спецификации для создания запросов к БД
 */
public class OfficeSpecification {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeSpecification.class.getName());

    /** Поиск офисов по параметрам
     * @param sorgId организации
     * @param name имя
     * @param phone телефон
     * @param isActive признак активности
     * @return условие выборки
     */
    public static Specification<Office> listBy(String sorgId, String name, String phone, String isActive) {
        return new Specification<Office>() {
            @Override
            public Predicate toPredicate(Root<Office> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (sorgId == null) {
                    LOGGER.error(String.format("There is no field to find organization (orgId):%s", sorgId));
                    throw new CantFindByNamePhoneActive(sorgId, name, phone, isActive);
                }
                int orgId;
                try {
                    orgId = Integer.parseInt(sorgId);
                } catch (NumberFormatException ex) {
                    throw new CantFindById(String.format(" wrong organization convert id=%s", sorgId));
                }
                predicates.add(cb.equal(root.get("organization"), orgId));
                if (name != null) {
                    predicates.add(cb.equal(root.get("name"), name));
                }
                if (phone != null) {
                    predicates.add(cb.equal(root.get("phone"), phone));
                }
                if (isActive != null) {
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                LOGGER.debug(String.format("Set specification to find offices"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
