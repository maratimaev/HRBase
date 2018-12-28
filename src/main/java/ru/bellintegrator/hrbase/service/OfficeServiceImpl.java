package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.exception.CantUpdateObject;
import ru.bellintegrator.hrbase.repository.OfficeRepository;
import ru.bellintegrator.hrbase.view.office.OfficeView;
import ru.bellintegrator.hrbase.view.organization.OrganizationView;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class OfficeServiceImpl implements GenericService<OfficeView, Office> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeServiceImpl.class.getName());

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private GenericService<OrganizationView, Organization> organizationService;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public OfficeView find(String id) {
        Office office = getById(id);
        if (office == null) {
            throw new CantFindByParam(String.format(" no such office id=%s", id));
        }
        return mapperFacade.map(office, OfficeView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OfficeView> list(OfficeView officeView) {
        List<Office> list = officeRepository.findAll(
                Specifications.listBy(officeView.getOrgId(), officeView.getName(), officeView.getPhone(), officeView.getIsActive()));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find offices by name=%s, phone=%s, isActive=%s and organization id=%s",
                    officeView.getName(), officeView.getPhone(), officeView.getIsActive(), officeView.getOrgId()));
            throw new CantFindByParam(String.format("name=%s, phone=%s, isActive=%s, organization id=%s",
                    officeView.getName(), officeView.getPhone(), officeView.getIsActive(), officeView.getOrgId()));
        }
        LOGGER.debug(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OfficeView.class)));
        return mapperFacade.mapAsList(list, OfficeView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(OfficeView officeView) {
        LOGGER.debug(String.format("Save office \n %s", officeView.toString()));
        Organization organization = organizationService.getById(officeView.getOrgId());
        try {
            Office office = mapperFacade.map(officeView, Office.class);
            office.setOrganization(organization);
            officeRepository.saveAndFlush(office);
        } catch (Exception ex) {
            throw new CantSaveNewObject("office");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(OfficeView officeView) {
        LOGGER.debug(String.format("Update office \n %s", officeView.toString()));
        Office office = getById(officeView.getId());
        if (office == null) {
            throw new CantFindByParam(String.format(" no such office id=%s", officeView.getId()));
        }
        mapperFacade.map(officeView, office);

        if (officeView.getOrgId() != null) {
            Organization organization = organizationService.getById(officeView.getOrgId());
            office.setOrganization(organization);
        }
        try {
            officeRepository.saveAndFlush(office);
        } catch (Exception ex) {
            throw new CantUpdateObject("office");
        }
    }

    /**
     * {@inheritDoc}
     */
    public Office getById(String sid) {
        Office office = null;
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException ex) {
            throw new CantFindByParam(String.format(" wrong office convert id=%s", sid));
        }
        Optional<Office> optional = officeRepository.findById(id);
        LOGGER.debug(String.format("Find office by id=%s \n result: %s", id, optional));
        if (optional.isPresent()) {
            office = optional.get();
        }
        return office;
    }
}
