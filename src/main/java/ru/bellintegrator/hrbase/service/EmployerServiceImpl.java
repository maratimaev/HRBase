package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.exception.CantUpdateObject;
import ru.bellintegrator.hrbase.exception.WrongDateFormat;
import ru.bellintegrator.hrbase.repository.DocumentRepository;
import ru.bellintegrator.hrbase.repository.EmployerRepository;
import ru.bellintegrator.hrbase.view.employer.EmployerView;
import ru.bellintegrator.hrbase.view.office.OfficeView;
import ru.bellintegrator.hrbase.view.result.Wrapper;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class EmployerServiceImpl implements GenericService<EmployerView, Employer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerServiceImpl.class.getName());

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private GenericService<OfficeView, Office> officeService;

    @Autowired
    private GenericGetByParamService<Country> citizenshipService;

    @Autowired
    private DocumentTypeServiceImpl documentTypeService;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<EmployerView> find(String id) {
        return new Wrapper<>(mapperFacade.map(getById(id), EmployerView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Wrapper<EmployerView> list(EmployerView employerView) {
        List<Employer> list = employerRepository.findAll(
                Specifications.listBy(employerView,
                        citizenshipService.getByCode(employerView.getCitizenshipCode()),
                        documentTypeService.getByCode(employerView.getDocCode())));

        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find employers by %s", employerView.toString()));
            throw new CantFindByParam(employerView.toString());
        }
        LOGGER.debug(String.format("Find employers \n %s", mapperFacade.mapAsList(list, EmployerView.class)));
        return new Wrapper<>(mapperFacade.mapAsList(list, EmployerView.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void save(EmployerView emplView) {
        LOGGER.debug(String.format("Save employer \n %s", emplView.toString()));
        Office office = officeService.getById(emplView.getOfficeId());
        Country country = citizenshipService.getByCode(emplView.getCitizenshipCode());
        DocumentType documentType = documentTypeService.getByCode(emplView.getDocCode());
        Document document = null;
        if (documentType != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(emplView.getDocDate());
                document = new Document(documentType, emplView.getDocNumber(), date);
                documentRepository.saveAndFlush(document);
            } catch (ParseException ex) {
                throw new WrongDateFormat(emplView.getDocDate());
            } catch (Exception ex) {
                throw new CantSaveNewObject("document");
            }
        }
        Employer employer = mapperFacade.map(emplView, Employer.class);
        employer.setOffice(office);
        employer.setCitizenship(country);
        employer.setDocument(document);
        try {
            employerRepository.saveAndFlush(employer);
        } catch (Exception ex) {
            throw new CantSaveNewObject("employer");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(EmployerView emplView) {
        LOGGER.debug(String.format("Update user \n %s", emplView.toString()));
        Country country = citizenshipService.getByCode(emplView.getCitizenshipCode());
        Employer employer = getById(emplView.getId());
        mapperFacade.map(emplView, employer);

        Document document = saveDocument(emplView);
        employer.setCitizenship(country);
        employer.setDocument(document);
        try {
            employerRepository.saveAndFlush(employer);
        } catch (Exception ex) {
            throw new CantUpdateObject("employer");
        }
    }

    /**
     * {@inheritDoc}
     */
    public Employer getById(String sid) {
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException ex) {
            throw new CantFindByParam(String.format(" wrong employer convert id=%s", sid));
        }

        Optional<Employer> optional = employerRepository.findById(id);
        LOGGER.debug(String.format("Find employer by id=%s \n result: %s", id, optional));
        if (!optional.isPresent()) {
            throw new CantFindByParam(String.format(" no such employer id=%s", id));
        }
        return optional.get();
    }

    private Document saveDocument(EmployerView emplView) {
        Document documentEntity = null;
        DocumentType documentType = documentTypeService.getByName(emplView.getDocName());
        if (documentType != null) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(emplView.getDocDate());
                Document documentView = new Document(documentType, emplView.getDocNumber(), date);
                documentEntity = documentRepository.findByNumber(emplView.getDocNumber());

                mapperFacade.map(documentView, documentEntity);
                documentRepository.saveAndFlush(documentEntity);
            } catch (ParseException ex) {
                throw new WrongDateFormat(emplView.getDocDate());
            } catch (Exception ex) {
                throw new CantSaveNewObject("document");
            }
        }
        return documentEntity;
    }
}
