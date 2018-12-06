package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.hrbase.Exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.OutputProfile.OrganizationProfile;
import ru.bellintegrator.hrbase.OutputProfile.WrapperProfile;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.Wrapper;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @JsonView(WrapperProfile.OrganizationFull.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<Organization> organizationById(@PathVariable String id) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.OrganizationBy(id));
        if(list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(list);
    }

    @JsonView(WrapperProfile.OrganizationShort.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Wrapper<Organization> organizationList(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String inn,
                                                  @RequestParam(required = false) String isActive){
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.listBy(name, inn, isActive));
        if(list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(list);
    }

}
