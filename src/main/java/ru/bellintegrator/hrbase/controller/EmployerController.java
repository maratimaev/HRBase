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
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.service.GenericService;
import ru.bellintegrator.hrbase.view.EmployerView;
import ru.bellintegrator.hrbase.view.profile.InputProfile;
import ru.bellintegrator.hrbase.view.profile.OutputProfile;
import ru.bellintegrator.hrbase.view.result.Success;

import java.util.List;

/**
 * Контроллеры для работы с сотрудниками
 */
@RestController
@RequestMapping(value = "/user")
public class EmployerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerController.class.getName());

    private final GenericService<EmployerView, Employer> employerService;

    @Autowired
    public EmployerController(GenericService<EmployerView, Employer> employerService) {
        this.employerService = employerService;
    }

    /** Поиск сотрудника по id
     * @param id сотрудника
     * @return View сотрудника
     */
    @JsonView(OutputProfile.Full.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployerView employerById(@PathVariable String id) {
        LOGGER.debug(String.format("Find employer by id=%s", id));
        return employerService.find(id);
    }

    /** Поиск сотрудника по параметрам
     * @param emplView объект json с constraints по полям
     * @return список сотрудников внутри List<EmployerView>
     */
    @JsonView(OutputProfile.Short.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployerView> getEmployers(@RequestBody @Validated({InputProfile.GetList.class}) EmployerView emplView) {
        LOGGER.debug(String.format("Get employer=%s", emplView.toString()));
        return employerService.list(emplView);
    }

    /** Сохранение нового сотрудника в БД
     * @param emplView объект json с constraints по полям
     * @return результат success
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success saveEmployer(@RequestBody @Validated({InputProfile.Save.class}) EmployerView emplView) {
        LOGGER.debug(String.format("Save employer with fields \n %s", emplView.toString()));
        employerService.save(emplView);
        return new Success();
    }

    /** Изменение параметров сотрудника
     * @param emplView объект json с constraints по полям
     * @return результат success
     */
    @PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Success updateEmployer(@RequestBody @Validated({InputProfile.Update.class}) EmployerView emplView) {
        LOGGER.debug(String.format("Update user with fields \n %s", emplView.toString()));
        employerService.update(emplView);
        return new Success();
    }
}
