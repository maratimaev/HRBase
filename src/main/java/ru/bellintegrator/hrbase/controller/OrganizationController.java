package ru.bellintegrator.hrbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.Map;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 02.12.2018
 */
@Controller
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @GetMapping("/organization")
    public String main(Map<String, Object> model) {
        Iterable<Organization> organizations = organizationRepository.findAll();
        model.put("organizations", organizations);
        return "organization";
    }

    @PostMapping("filter")
    public String filter(@RequestParam(name="name", required=true) String name,
                         @RequestParam(name="inn", required=false) String inn,
                         @RequestParam(name="isActive", required=false) boolean isActive,
                         Map<String, Object> model){
        Iterable<Organization> organizations;
        if(name != null && !name.isEmpty()){
            organizations = organizationRepository.findOrganizationsByName(name);
            //organizations = organizationRepository.findOrganizationsByIsActiveTrue();
        } else {
            organizations = organizationRepository.findAll();
        }
        model.put("organizations", organizations);
        return "organization";
    }
}
