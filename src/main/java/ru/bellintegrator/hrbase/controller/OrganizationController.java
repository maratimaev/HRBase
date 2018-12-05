package ru.bellintegrator.hrbase.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.hrbase.Exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.OutputProfile.OrganizationProfile;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 02.12.2018
 */
@RestController
@RequestMapping(value = "/organization")
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;
    private OrganizationSpecification organizationSpecification;

    @JsonView(OrganizationProfile.Full.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public Organization organizationById(@PathVariable String id) throws ThereIsNoSuchOrganization {
        int i;
        try {
            i = Integer.parseInt(id);
        } catch (Exception ex) {
            throw new ThereIsNoSuchOrganization();
        }
        Optional<Organization> org = organizationRepository.findById(i);
        if (!org.isPresent()) {
            throw new ThereIsNoSuchOrganization();
        }
        return org.get();
    }

    @JsonView(OrganizationProfile.Full.class)
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Organization> organizationList(@RequestParam String name, @RequestParam String inn, @RequestParam String isActive){
        List<Organization> organizations = organizationRepository.findAll(organizationSpecification.organizationList(name, inn, isActive));
        return organizations;
    }

//    @JsonView(OrganizationProfile.Full.class)
//    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Wrapper<Organization> organizationList() {
//        return new Wrapper<>(organizationRepository.findAll());
//    }

}
