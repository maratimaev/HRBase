package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.service.GenericService;
import ru.bellintegrator.hrbase.view.organization.OrganizationView;
import ru.bellintegrator.hrbase.view.organization.OrganizationViewList;
import ru.bellintegrator.hrbase.view.organization.OrganizationViewSave;
import ru.bellintegrator.hrbase.view.organization.OrganizationViewUpdate;
import ru.bellintegrator.hrbase.view.result.Error;
import ru.bellintegrator.hrbase.view.result.Result;
import ru.bellintegrator.hrbase.view.result.Success;

import javax.validation.Valid;

/**
 * Контроллеры для работы с организациями
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class.getName());

    @Autowired
    private GenericService<OrganizationView, Organization> organizationService;

    /** Поиск организации по id
     * @param id организации
     * @return View организации
     */
    @JsonView(WrapperProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrganizationView organizationById(@PathVariable String id) {
        LOGGER.debug(String.format("Find organization by id=%s", id));
        return organizationService.find(id);
    }

    /** Поиск организаций по параметрам
     * @param orgViewList объект json с constraints по полям
     * @return список организаций внутри List<OrganizationView> или Error
     */
    @JsonView(WrapperProfile.Short.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object getOrganizations(@RequestBody @Valid OrganizationViewList orgViewList, BindingResult bindingResult) {
        Object result;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't find organizations : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()), HttpStatus.NOT_ACCEPTABLE);
        } else {
            LOGGER.debug(String.format("Get organizations by name=%s, inn=%s, isActive=%s", orgViewList.getName(), orgViewList.getInn(), orgViewList.getIsActive()));
            result = organizationService.list(orgViewList);
        }
        return result;
    }


    /** Сохранение новой организации в БД
     * @param orgViewSave объект json c constraints
     * @return результат success/error
     */
    @JsonView(WrapperProfile.Data.class)
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result saveOrganization(@RequestBody @Valid OrganizationViewSave orgViewSave, BindingResult bindingResult) {
        Result result = new Success();
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't save organization : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()), HttpStatus.NOT_ACCEPTABLE);
        } else {
            LOGGER.debug(String.format("Save organization with fields \n %s", orgViewSave.toString()));
            organizationService.save(orgViewSave);
        }
        return result;
    }

    /** Изменение параметров организации
     * @param orgViewUpdate объект json
     * @param bindingResult результат валидации
     * @return результат success/error
     */
    @JsonView(WrapperProfile.Data.class)
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result updateOrganization(@RequestBody @Valid OrganizationViewUpdate orgViewUpdate, BindingResult bindingResult) {
        Result result = new Success();
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't update organization : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()), HttpStatus.NOT_ACCEPTABLE);
        } else {
            LOGGER.debug(String.format("Update organization with fields \n %s", orgViewUpdate.toString()));
            organizationService.update(orgViewUpdate);
        }
        return result;
    }
}
