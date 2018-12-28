package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.exception.CantUpdateObject;
import ru.bellintegrator.hrbase.repository.OrganizationRepository;
import ru.bellintegrator.hrbase.view.organization.OrganizationView;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class OrganizationServiceImpl implements GenericService<OrganizationView, Organization> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class.getName());

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationView find(String id) {
        return mapperFacade.map(getById(id), OrganizationView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrganizationView> list(OrganizationView orgView) {
        List<Organization> list = organizationRepository.findAll(
                Specifications.listBy(orgView.getName(), orgView.getInn(), orgView.getIsActive()));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find organization by name=%s, inn=%s, isActive=%s", orgView.getName(), orgView.getInn(), orgView.getIsActive()));
            throw new CantFindByParam(String.format("name=%s, inn=%s, isActive=%s", orgView.getName(), orgView.getInn(), orgView.getIsActive()));
        }
        LOGGER.debug(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OrganizationView.class)));
        return mapperFacade.mapAsList(list, OrganizationView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(OrganizationView orgView) {
        LOGGER.debug(String.format("Save organizations \n %s", orgView.toString()));
        try {
            organizationRepository.saveAndFlush(mapperFacade.map(orgView, Organization.class));
        } catch (Exception ex) {
            throw new CantSaveNewObject("organization");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(OrganizationView orgView) {
        LOGGER.debug(String.format("Update organizations \n %s", orgView.toString()));
        Organization organization = getById(orgView.getId());
        mapperFacade.map(orgView, organization);
        try {
            organizationRepository.saveAndFlush(organization);
        } catch (Exception ex) {
            throw new CantUpdateObject("organization");
        }
    }

    /**
     * {@inheritDoc}
     */
    public Organization getById(String sid) {
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException ex) {
            throw new CantFindByParam(String.format("wrong organization convert id=%s", sid));
        }
        Optional<Organization> optional = organizationRepository.findById(id);
        LOGGER.debug(String.format("Find organization by id=%s \n result: %s", id, optional));
        if (!optional.isPresent()) {
            throw new CantFindByParam(String.format("no such organization id=%s", id));
        }
        return optional.get();
    }
}
