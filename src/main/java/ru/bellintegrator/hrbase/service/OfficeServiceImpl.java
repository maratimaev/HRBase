package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.entity.Organization;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindById;
import ru.bellintegrator.hrbase.exception.CantFindByNamePhoneActive;
import ru.bellintegrator.hrbase.exception.CantSaveNewOffice;
import ru.bellintegrator.hrbase.exception.CantUpdateOffice;
import ru.bellintegrator.hrbase.exception.OrganizationsIdMustBeEquals;
import ru.bellintegrator.hrbase.repository.OfficeRepository;
import ru.bellintegrator.hrbase.service.specification.OfficeSpecification;
import ru.bellintegrator.hrbase.view.office.OfficeView;
import ru.bellintegrator.hrbase.view.result.Wrapper;

import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class OfficeServiceImpl implements OfficeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeServiceImpl.class.getName());

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
//    private OrganizationRepository organizationRepository;
    private OrganizationService organizationService;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OfficeView> findOfficeById(String id) {
        return new Wrapper<>(mapperFacade.map(getOfficeById(id), OfficeView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<OfficeView> getOffices(String orgId, OfficeView officeView) {
        if (!orgId.equals(officeView.getOrgId())) {
            throw new OrganizationsIdMustBeEquals(orgId, officeView.getOrgId());
        }
        List<Office> list = officeRepository.findAll(
                OfficeSpecification.listBy(orgId, officeView.getName(), officeView.getPhone(), officeView.getIsActive()));
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find offices by name=%s, phone=%s, isActive=%s and organization id=%s",
                    officeView.getName(), officeView.getPhone(), officeView.getIsActive(), orgId));
            throw new CantFindByNamePhoneActive(orgId, officeView.getName(), officeView.getPhone(), officeView.getIsActive());
        }
        LOGGER.debug(String.format("Find organizations \n %s", mapperFacade.mapAsList(list, OfficeView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, OfficeView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveOffice(OfficeView officeView) {
        LOGGER.debug(String.format("Save office \n %s", officeView.toString()));
        Organization organization = organizationService.getOrgById(officeView.getOrgId());
        try {
            Office office = mapperFacade.map(officeView, Office.class);
            office.setOrganization(organization);
            officeRepository.saveAndFlush(office);
        } catch (Exception ex) {
            throw new CantSaveNewOffice();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateOffice(OfficeView officeView) {
        Office office = getOfficeById(officeView.getId());
        mapperFacade.map(officeView, office);

        if (officeView.getOrgId() != null) {
            Organization organization = organizationService.getOrgById(officeView.getOrgId());
            office.setOrganization(organization);
        }
        try {
            officeRepository.saveAndFlush(office);
        } catch (Exception ex) {
            throw new CantUpdateOffice();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Office getOfficeById(String sid) {
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException ex) {
            throw new CantFindById(String.format(" wrong office convert id=%s", sid));
        }
        Optional<Office> optional = officeRepository.findById(id);
        LOGGER.debug(String.format("Find office by id=%s \n result: %s", id, optional));
        if (!optional.isPresent()) {
            throw new CantFindById(String.format(" no such office id=%s", id));
        }
        return optional.get();
    }
}
