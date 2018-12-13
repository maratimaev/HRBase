package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.view.status.Error;
import ru.bellintegrator.hrbase.view.status.Result;
import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.service.OrganizationService;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.status.Success;

import javax.validation.Valid;

/**
 * Контроллеры для работы с организациями
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class.getName());

    @Autowired
    private OrganizationService organizationService;

    /** Поиск организации по id
     * @param id организации
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationFull.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<OrganizationView> organizationById(@PathVariable String id) {
        log.info(String.format("Find organization by id=%s", id));
        return organizationService.findOrganizationById(id);
    }

    /** Поиск организаций по параметрам
     * @param name имя
     * @param inn ИНН
     * @param isActive признак активности
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationShort.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<OrganizationView> getOrganizations(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String inn,
                                                      @RequestParam(required = false) String isActive) {
        log.info(String.format("Get list of organizations by name=%s, inn=%s, isActive=%s", name, inn, isActive));
        return organizationService.getOrganizations(name, inn, isActive);
    }

    /** Сохранение новой организации в БД
     * @param organizationView объект json
     * @return результат
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result saveOrganization(@RequestBody @Valid OrganizationView organizationView, BindingResult bindingResult) {
        Result result = new Success();
        if (bindingResult.hasErrors()) {
            log.error(String.format("Can't save organization : \n %s", bindingResult.toString()));
            result = new Error("wrong validation of required fields: name, fullName, inn, kpp, address");
        } else {
            log.info(String.format("Save organization with fields \n %s", organizationView.toString()));
            organizationService.saveOrganization(organizationView);
        }
        return result;
    }

    /** Изменение параметорв организации
     * @param organizationView объект json
     * @param bindingResult результат валидации
     * @return результат
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result updateOrganization(@RequestBody @Valid OrganizationView organizationView, BindingResult bindingResult) {
        Boolean viewValid = true;
        Result result = new Success();
        if (organizationView.getId() <= 0) {
            log.error(String.format("Can't update organization by negative id=%s", organizationView.getId()));
            result = new Error("wrong organization ID");
            viewValid = false;
        }
        if (bindingResult.hasErrors()) {
            log.error(String.format("Can't update organization : \n %s", bindingResult.toString()));
            result = new Error("wrong validation of required fields: id, name, fullName, inn, kpp, address");
            viewValid = false;
        }
        if (viewValid) {
            log.info(String.format("Update organization with fields \n %s", organizationView.toString()));
            organizationService.updateOrganization(organizationView);
        }
        return result;
    }
}
