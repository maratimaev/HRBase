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
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.service.GenericService;
import ru.bellintegrator.hrbase.view.employer.EmployerView;
import ru.bellintegrator.hrbase.view.employer.EmployerViewList;
import ru.bellintegrator.hrbase.view.employer.EmployerViewSave;
import ru.bellintegrator.hrbase.view.employer.EmployerViewUpdate;
import ru.bellintegrator.hrbase.view.result.Error;
import ru.bellintegrator.hrbase.view.result.Result;
import ru.bellintegrator.hrbase.view.result.Success;

import javax.validation.Valid;

/**
 * Контроллеры для работы с сотрудниками
 */
@RestController
@RequestMapping(value = "/user")
public class EmployerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class.getName());

    @Autowired
    private GenericService<EmployerView, Employer> employerService;

    /** Поиск сотрудника по id
     * @param id сотрудника
     * @return сотрудник внутри Wrapper
     */
    @JsonView(WrapperProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> employerById(@PathVariable String id) {
        LOGGER.debug(String.format("Find employer by id=%s", id));
        return new ResponseEntity<> (employerService.find(id), HttpStatus.OK);
    }

    /** Поиск сотрудника по параметрам
     * @param emplViewList объект json с constraints по полям
     * @return список офисов внутри Wrapper<EmployerView>
     */
    @JsonView(WrapperProfile.Short.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> getEmployers(@RequestBody @Valid EmployerViewList emplViewList, BindingResult bindingResult) {
        Result result;
        HttpStatus status = HttpStatus.OK;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't find employers : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Get employer=%s", emplViewList.toString()));
            result = employerService.list(emplViewList);
        }
        return new ResponseEntity<>(result, status);
    }

    /** Сохранение нового сотрудника в БД
     * @param emplViewSave объект json c constraints
     * @return результат success/error
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> saveEmployer(@RequestBody @Valid EmployerViewSave emplViewSave, BindingResult bindingResult) {
        Result result = new Success();
        HttpStatus status = HttpStatus.CREATED;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't save employer : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Save employer with fields \n %s", emplViewSave.toString()));
            employerService.save(emplViewSave);
        }
        return new ResponseEntity<>(result, status);
    }

    /** Изменение параметров сотрудника
     * @param emplViewUpdate объект json
     * @param bindingResult результат валидации
     * @return результат
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result> updateEmployer(@RequestBody @Valid EmployerViewUpdate emplViewUpdate, BindingResult bindingResult) {
        Result result = new Success();
        HttpStatus status = HttpStatus.ACCEPTED;
        if (bindingResult.hasErrors()) {
            LOGGER.error(String.format("Can't update user : \n %s", bindingResult.toString()));
            FieldError error = bindingResult.getFieldErrors().get(0);
            result = new Error(String.format("Field (%s) can't be: %s", error.getField(), error.getRejectedValue()));
            status = HttpStatus.NOT_ACCEPTABLE;
        } else {
            LOGGER.debug(String.format("Update user with fields \n %s", emplViewUpdate.toString()));
            employerService.update(emplViewUpdate);
        }
        return new ResponseEntity<>(result, status);
    }
}
