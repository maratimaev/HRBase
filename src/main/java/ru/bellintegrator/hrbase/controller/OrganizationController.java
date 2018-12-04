package ru.bellintegrator.hrbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.Wrapper;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.List;

import static ru.bellintegrator.hrbase.controller.OrganizationSpecification.organizationList;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 02.12.2018
 */
@RestController
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @RequestMapping(value = "/organization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<Organization> main() {
        return new Wrapper<>(organizationRepository.findAll());
    }


    @RequestMapping(value = "/organization/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Organization> filter(@RequestParam String name,
                                     @RequestParam String inn,
                                     @RequestParam String isActive){

        List<Organization> organizations;
        organizations = organizationRepository.findAll(organizationList(name, inn, isActive));
        return organizations;
    }


}
