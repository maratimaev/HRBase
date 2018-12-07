package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.Wrapper;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.List;

/**
 * Контроллеры для работы с организациями
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    /** Поиск организации по id
     * @param id организации
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationFull.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<Organization> organizationById(@PathVariable String id) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.organizationBy(id));
        if (list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(list);
    }

    /** Поиск организаций по параметрам
     * @param name имя
     * @param inn ИНН
     * @param isActive признак активности
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationShort.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<Organization> organizationList(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String inn,
                                                  @RequestParam(required = false) String isActive) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.listBy(name, inn, isActive));
        if (list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(list);
    }

}
