package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindById;
import ru.bellintegrator.hrbase.exception.CantFindByNameInnActive;
import ru.bellintegrator.hrbase.exception.CantSaveNewOrganization;
import ru.bellintegrator.hrbase.exception.CantUpdateOrganization;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;
import ru.bellintegrator.hrbase.service.specification.OrganizationSpecification;
import ru.bellintegrator.hrbase.view.OrganizationView;
import ru.bellintegrator.hrbase.view.result.Wrapper;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class.getName());

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OrganizationView> findOrganizationById(String id) {
        List<Organization> list = organizationRepository.findAll(OrganizationSpecification.organizationBy(id));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find organization by id=%s", id));
            throw new CantFindById(id);
        }
        LOGGER.debug(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OrganizationView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OrganizationView> getOrganizations(OrganizationView orgView) {
        List<Organization> list = organizationRepository.findAll(
                OrganizationSpecification.listBy(orgView.getName(), orgView.getInn(), orgView.getIsActive()));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find organization by name=%s, inn=%s, isActive=%s", orgView.getName(), orgView.getInn(), orgView.getIsActive()));
            throw new CantFindByNameInnActive(orgView.getName(), orgView.getInn(), orgView.getIsActive());
        }
        LOGGER.debug(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OrganizationView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, OrganizationView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveOrganization(OrganizationView orgView) {
        LOGGER.debug(String.format("Save organizations \n %s", orgView.toString()));
        try {
            organizationRepository.saveAndFlush(mapperFacade.map(orgView, Organization.class));
        } catch (Exception ex) {
            throw new CantSaveNewOrganization();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOrganization(OrganizationView orgView) {
        int id;
        try {
            id = Integer.parseInt(orgView.getId());
        } catch (NumberFormatException ex) {
            throw new CantUpdateOrganization();
        }
        Optional<Organization> optional = organizationRepository.findById(id);
        LOGGER.debug(String.format("Find organization for updating \n %s \n result: %s", orgView.toString(), optional));
        if (!optional.isPresent()) {
            throw new CantFindById(" " + orgView.getId());
        }
        Organization organization = optional.get();
        mapperFacade.map(orgView, organization);
        try {
            organizationRepository.saveAndFlush(organization);
        } catch (Exception ex) {
            throw new CantUpdateOrganization();
        }
    }
}
