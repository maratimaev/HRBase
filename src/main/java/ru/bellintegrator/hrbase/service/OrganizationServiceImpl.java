package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.ThereIsNoSuchOrganization;
import ru.bellintegrator.hrbase.service.specification.OrganizationSpecification;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.view.Wrapper;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;
import ru.bellintegrator.hrbase.view.OrganizationView;


import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class.getName());

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
            LOGGER.error(String.format("Can't find organization by id=%s", id));
            throw new ThereIsNoSuchOrganization();
        }
        LOGGER.info(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OrganizationView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OrganizationView> getOrganizations(String name, String inn, String isActive) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.listBy(name, inn, isActive));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find organization by name=%s, inn=%s, isActive=%s", name, inn, isActive));
            throw new ThereIsNoSuchOrganization();
        }
        LOGGER.info(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OrganizationView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOrganization(OrganizationView organizationView) {
        LOGGER.info(String.format("Save organizations \n %s", organizationView.toString()));
        organizationRepository.saveAndFlush(mapperFacade.map(organizationView, Organization.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateOrganization(OrganizationView organizationView) {
        Optional<Organization> optional = organizationRepository.findById(organizationView.getId());
        LOGGER.info(String.format("Find organization for updating \n %s \n result: %s", organizationView.toString(), optional));
        if (!optional.isPresent()) {
            throw new ThereIsNoSuchOrganization();
        }
        Organization organization = optional.get();
        mapperFacade.map(organizationView, organization);
        organizationRepository.saveAndFlush(organization);
    }
}
