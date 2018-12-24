package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.service.OfficeService;
import ru.bellintegrator.hrbase.view.office.OfficeViewList;
import ru.bellintegrator.hrbase.view.office.OfficeViewSave;
import ru.bellintegrator.hrbase.view.office.OfficeViewUpdate;
import ru.bellintegrator.hrbase.view.result.Error;
import ru.bellintegrator.hrbase.view.result.Result;
import ru.bellintegrator.hrbase.view.result.Success;

import javax.validation.Valid;

/**
 * Контроллеры для работы с офисами
 */
@RestController
@RequestMapping(value = "/office")
public class OfficeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeController.class.getName());

    @Autowired
    private OfficeService officeService;

    /** Поиск офиса по id
     * @param id офиса
     * @return офис внутри Wrapper
     */
    @JsonView(WrapperProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> officeById(@PathVariable String id) {
        LOGGER.debug(String.format("Find office by id=%s", id));
        return new ResponseEntity<>(officeService.findOfficeById(id), HttpStatus.OK);
    }

    /** Поиск офиса по параметрам
     * @param officeViewList объект json с constraints по полям
     * @return список офисов внутри Wrapper<OfficeView>
     */
    @JsonView(WrapperProfile.Short.class)
    @PostMapping(value = "/list/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> getOrganizations(@RequestBody @Valid OfficeViewList officeViewList,
                                                   @PathVariable String id, BindingResult bindingResult) {
        Result result;
        HttpStatus status = HttpStatus.OK;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't find offices : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Get offices by organization id=%s, offices name=%s, phone=%s, isActive=%s",
                    id, officeViewList.getName(), officeViewList.getPhone(), officeViewList.getIsActive()));
            result = officeService.getOffices(id, officeViewList);
        }
        return new ResponseEntity<>(result, status);
    }

    /** Сохранение нового офиса в БД
     * @param officeViewSave объект json c constraints
     * @return результат success/error
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> saveOrganization(@RequestBody @Valid OfficeViewSave officeViewSave, BindingResult bindingResult) {
        Result result = new Success();
        HttpStatus status = HttpStatus.CREATED;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't save office : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Save office with fields \n %s", officeViewSave.toString()));
            officeService.saveOffice(officeViewSave);
        }
        return new ResponseEntity<>(result, status);
    }

    /** Изменение параметров офиса
     * @param officeViewUpdate объект json
     * @param bindingResult результат валидации
     * @return результат
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> updateOffice(@RequestBody @Valid OfficeViewUpdate officeViewUpdate, BindingResult bindingResult) {
        Result result = new Success();
        HttpStatus status = HttpStatus.ACCEPTED;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't update office : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Update office with fields \n %s", officeViewUpdate.toString()));
            officeService.updateOffice(officeViewUpdate);
        }
        return new ResponseEntity<>(result, status);
    }
}
