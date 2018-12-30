package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.view.employer.EmployerView;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Спецификации для создания запросов к БД
 */
public class Specifications {
    private static final Logger LOGGER = LoggerFactory.getLogger(Specifications.class.getName());

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
                    throw new CantFindByParam(String.format("orgId=%s", sorgId));
                }
                int orgId;
                try {
                    orgId = Integer.parseInt(sorgId);
                } catch (NumberFormatException ex) {
                    throw new CantFindByParam(String.format(" wrong organization convert id=%s", sorgId));
                }
                predicates.add(cb.equal(root.get("organization"), orgId));
                if (name != null && !name.isEmpty()) {
                    predicates.add(cb.equal(root.get("name"), name));
                }
                if (phone != null && !phone.isEmpty()) {
                    predicates.add(cb.equal(root.get("phone"), phone));
                }
                if (isActive != null && !isActive.isEmpty()) {
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                LOGGER.debug(String.format("Set specification to find offices"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }

    /** Поиск сотрудников по параметрам
     * @param employerView сотрудник
     * @param country гражданство
     * @param document документ
     * @return условие выборки
     */
    public static Specification<Employer> listBy(EmployerView employerView, Office office, Country country, Document document) {
        return new Specification<Employer>() {
            @Override
            public Predicate toPredicate(Root<Employer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                try {
                    if (office != null) {
                        predicates.add(cb.equal(root.get("office"), office.getId()));
                    }
                    if (document != null) {
                        predicates.add(cb.equal(root.get("document"), document.getId()));
                    }
                    if (country != null) {
                        predicates.add(cb.equal(root.get("citizenship"), country.getId()));
                    }
                } catch (NumberFormatException ex) {
                    throw new CantFindByParam(String.format(" wrong convert officeId=%s or docCode=%s or citizenshipCode=%s",
                            employerView.getOfficeId(), employerView.getDocCode(), employerView.getCitizenshipCode()));
                }
                String firstName = employerView.getFirstName();
                if (firstName != null && !firstName.isEmpty()) {
                    predicates.add(cb.equal(root.get("firstName"), firstName));
                }
                String lastName = employerView.getLastName();
                if (lastName != null && !lastName.isEmpty()) {
                    predicates.add(cb.equal(root.get("lastName"), lastName));
                }
                String middleName = employerView.getMiddleName();
                if (middleName != null && !middleName.isEmpty()) {
                    predicates.add(cb.equal(root.get("middleName"), middleName));
                }
                String position = employerView.getPosition();
                if (position != null && !position.isEmpty()) {
                    predicates.add(cb.equal(root.get("position"), position));
                }
                LOGGER.debug(String.format("Set specification to find employers"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
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
                    LOGGER.error(String.format("There is no field to find organization (name):%s", name));
                    throw new CantFindByParam(String.format("name=%s", name));
                }
                predicates.add(cb.equal(root.get("name"), name));
                if (inn != null && !inn.isEmpty()) {
                    predicates.add(cb.equal(root.get("inn"), inn));
                }
                if (isActive != null && !isActive.isEmpty()) {
                    predicates.add(cb.equal(root.get("isActive"), Boolean.parseBoolean(isActive)));
                }
                LOGGER.debug(String.format("Set specification to find organization"));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
