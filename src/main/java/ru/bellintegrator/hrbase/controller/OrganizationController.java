package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.service.GenericService;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;
import ru.bellintegrator.hrbase.view.result.Success;

import java.util.List;

/**
 * Контроллеры для работы с организациями
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class.getName());

    private final GenericService<OrganizationView, Organization> organizationService;

    @Autowired
    public OrganizationController(GenericService<OrganizationView, Organization> organizationService) {
        this.organizationService = organizationService;
    }

    /** Поиск организации по id
     * @param id организации
     * @return View организации
     */
    @JsonView(OutputProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationView organizationById(@PathVariable String id) {
        LOGGER.debug(String.format("Find organization by id=%s", id));
        return organizationService.find(id);
    }

    /** Поиск организаций по параметрам
     * @param orgView объект json с constraints по полям
     * @return список организаций внутри List<OrganizationView> или Error
     */
    @JsonView(OutputProfile.Short.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OrganizationView> getOrganizations(@RequestBody @Validated({InputProfile.GetList.class}) OrganizationView orgView) {
        LOGGER.debug(String.format("Get organizations by name=%s, inn=%s, isActive=%s", orgView.getName(), orgView.getInn(), orgView.getIsActive()));
        return organizationService.list(orgView);
    }

    /** Сохранение новой организации в БД
     * @param orgView объект json c constraints
     * @return результат success
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success saveOrganization(@RequestBody @Validated({InputProfile.Save.class}) OrganizationView orgView) {
        LOGGER.debug(String.format("Save organization with fields \n %s", orgView.toString()));
        organizationService.save(orgView);
        return new Success();
    }

    /** Изменение параметров организации
     * @param orgView объект json
     * @return результат success
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success updateOrganization(@RequestBody @Validated({InputProfile.Update.class}) OrganizationView orgView) {
        LOGGER.debug(String.format("Update organization with fields \n %s", orgView.toString()));
        organizationService.update(orgView);
        return new Success();
    }
}
