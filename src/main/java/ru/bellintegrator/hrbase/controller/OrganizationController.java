package ru.bellintegrator.hrbase.controller;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;

import java.util.List;
import java.util.Map;

/**
 * @author Marat Imaev (mailto:imaevmarat@outlook.com)
 * @since 02.12.2018
 */
@RestController
public class OrganizationController {
    @Autowired
    private OrganizationRepository organizationRepository;

    @RequestMapping(value = "/organization", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Organization> main() {
        List<Organization> list = organizationRepository.findAll();
        return organizationRepository.findAll();
    }

//    @PostMapping("filter")
//    public String filter(@RequestParam String name,
//                         @RequestParam String inn,
//                         @RequestParam boolean isActive,
//                         Map<String, Object> model){
//        Iterable<Organization> organizations;
//        if(name != null && !name.isEmpty()){
//            organizations = organizationRepository.findOrganizationsByName(name);
//            //organizations = organizationRepository.findOrganizationsByIsActiveTrue();
//        } else {
//            organizations = organizationRepository.findAll();
//        }
//        model.put("organizations", organizations);
//        return "organization";
//    }
}
