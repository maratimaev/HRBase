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
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.exception.OrganizationsIdMustBeEquals;
import ru.bellintegrator.hrbase.service.GenericService;
import ru.bellintegrator.hrbase.view.OfficeView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;
import ru.bellintegrator.hrbase.view.result.Success;

import java.util.List;

/**
 * Контроллеры для работы с офисами
 */
@RestController
@RequestMapping(value = "/office")
public class OfficeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeController.class.getName());

    private final GenericService<OfficeView, Office> officeService;

    @Autowired
    public OfficeController(GenericService<OfficeView, Office> officeService) {
        this.officeService = officeService;
    }

    /** Поиск офиса по id
     * @param id офиса
     * @return View офиса
     */
    @JsonView(OutputProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OfficeView officeById(@PathVariable String id) {
        LOGGER.debug(String.format("Find office by id=%s", id));
        return officeService.find(id);
    }

    /** Поиск офиса по параметрам
     * @param officeView объект json с constraints по полям
     * @return список офисов внутри List<OfficeView>
     */
    @JsonView(OutputProfile.Short.class)
    @PostMapping(value = "/list/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<OfficeView> getOrganizations(@RequestBody @Validated({InputProfile.GetList.class}) OfficeView officeView, @PathVariable String id) {
        if (!id.equals(officeView.getOrgId())) {
            throw new OrganizationsIdMustBeEquals(id, officeView.getOrgId());
        }
        LOGGER.debug(String.format("Get offices by organization id=%s, offices name=%s, phone=%s, isActive=%s",
                    id, officeView.getName(), officeView.getPhone(), officeView.getIsActive()));
        return officeService.list(officeView);
    }

    /** Сохранение нового офиса в БД
     * @param officeView объект json с constraints по полям
     * @return результат success
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success saveOrganization(@RequestBody @Validated({InputProfile.Save.class}) OfficeView officeView) {
        LOGGER.debug(String.format("Save office with fields \n %s", officeView.toString()));
        officeService.save(officeView);
        return new Success();
    }

    /** Изменение параметров офиса
     * @param officeView объект json с constraints по полям
     * @return результат success
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success updateOffice(@RequestBody @Validated({InputProfile.Update.class}) OfficeView officeView) {
        LOGGER.debug(String.format("Update office with fields \n %s", officeView.toString()));
        officeService.update(officeView);
        return new Success();
    }
}
