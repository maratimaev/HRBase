package ru.bellintegrator.hrbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.service.specification.OrganizationSpecification;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;
import ru.bellintegrator.hrbase.view.OrganizationView;

import java.util.List;

/**
 * {@inheritDoc}
 */

@Service
public class OrganizationServiceImpl implements OrganizationService{

    private OrganizationRepository organizationRepository;
    private MapperFacade mapperFacade;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository, MapperFacade mapperFacade) {
        this.organizationRepository = organizationRepository;
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OrganizationView> findOrganizationById(String id) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.organizationBy(id));
        if (list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OrganizationView> getOrganizations(String name, String inn, String isActive) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.listBy(name, inn, isActive));
        if (list.isEmpty()) {
            throw new ThereIsNoSuchOrganization();
        }
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOrganization(OrganizationView organizationView) {
        organizationRepository.saveAndFlush(mapperFacade.map(organizationView, Organization.class));
    }
}
