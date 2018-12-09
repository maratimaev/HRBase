package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.hrbase.profile.WrapperProfile;
import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.service.OrganizationService;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.ResultResponse;

/**
 * Контроллеры для работы с организациями
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    /** Поиск организации по id
     * @param id организации
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationFull.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<OrganizationView> organizationById(@PathVariable String id) {
        return organizationService.findOrganizationById(id);
    }

    /** Поиск организаций по параметрам
     * @param name имя
     * @param inn ИНН
     * @param isActive признак активности
     * @return список организаций внутри Wrapper
     */
    @JsonView(WrapperProfile.OrganizationShort.class)
    @PostMapping("list")
    public Wrapper<OrganizationView> getOrganizations(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String inn,
                                                      @RequestParam(required = false) String isActive) {
        return organizationService.getOrganizations(name, inn, isActive);
    }

    /** Сохранение новой организации в БД
     * @param organizationView объект json
     * @return в случае успеха
     */
    @PostMapping("save")
    public ResultResponse saveOrganization(@RequestBody OrganizationView organizationView) {
        organizationService.saveOrganization(organizationView);
        return new ResultResponse("success");
    }

}
