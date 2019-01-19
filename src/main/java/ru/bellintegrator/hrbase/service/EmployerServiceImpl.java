package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.exception.CantUpdateObject;
import ru.bellintegrator.hrbase.repository.DocumentRepository;
import ru.bellintegrator.hrbase.repository.EmployerRepository;
import ru.bellintegrator.hrbase.view.EmployerView;
import ru.bellintegrator.hrbase.view.OfficeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */

@Service
public class EmployerServiceImpl implements GenericService<EmployerView, Employer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerServiceImpl.class.getName());

    private final EmployerRepository employerRepository;

    private final DocumentRepository documentRepository;

    private final GenericService<OfficeView, Office> officeService;

    private final GenericGetByParamService<Country> citizenshipService;

    private final GenericGetByNameService<Document> documentService;

    private final GenericGetByNameService<DocumentType> documentTypeService;

    private final MapperFacade mapperFacade;

    @Autowired
    public EmployerServiceImpl(EmployerRepository employerRepository, DocumentRepository documentRepository, GenericService<OfficeView, Office> officeService, GenericGetByParamService<Country> citizenshipService, GenericGetByNameService<Document> documentService, GenericGetByNameService<DocumentType> documentTypeService, MapperFacade mapperFacade) {
        this.employerRepository = employerRepository;
        this.documentRepository = documentRepository;
        this.officeService = officeService;
        this.citizenshipService = citizenshipService;
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public EmployerView find(String id) {
        return mapperFacade.mapToEmployer(getById(id), new EmployerView());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmployerView> list(EmployerView employerView) {
        List<Employer> list = employerRepository.findAll(
                Specifications.listBy(
                    employerView,
                    officeService.getById(employerView.getOfficeId()),
                    citizenshipService.getByCode(employerView.getCitizenshipCode()),
                    documentService.getByName(employerView.getDocName())
                )
        );
        if (list.isEmpty()) {
            LOGGER.error(String.format("Can't find employers by %s", employerView.toString()));
            throw new CantFindByParam(employerView.toString());
        }
        LOGGER.debug(String.format("Find employers \n %s", mapperFacade.mapAsList(list, EmployerView.class)));
        List<EmployerView> viewList = new ArrayList<>();
        for (Employer e : list) {
            viewList.add(mapperFacade.mapToEmployer(e, new EmployerView()));
        }
        return viewList;
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
        Employer employer = mapperFacade.map(emplView, Employer.class);
        employer.setOffice(office);
        employer.setCitizenship(country);
        employer.setDocument(saveDocument(emplView));
        try {
            employerRepository.saveAndFlush(employer);
        } catch (Exception ex) {
            throw new CantSaveNewObject("employer", ex);
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
        employer.setCitizenship(country);
        employer.setDocument(saveDocument(emplView));
        try {
            employerRepository.saveAndFlush(employer);
        } catch (Exception ex) {
            throw new CantUpdateObject("employer", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Employer getById(String sid) {
        int id;
        try {
            id = Integer.parseInt(sid);
        } catch (NumberFormatException ex) {
            throw new CantFindByParam(String.format("wrong user convert id=%s", sid), ex);
        }
        Optional<Employer> optional = employerRepository.findById(id);
        LOGGER.debug(String.format("Find user by id=%s \n result: %s", id, optional));
        if (!optional.isPresent()) {
            throw new CantFindByParam(String.format("no such user id=%s", id));
        }
        return optional.get();
    }

    /** Сохранение документа
     * @param emplView данные сотрудника
     * @return документ
     */
    @Transactional
    public Document saveDocument(EmployerView emplView) {
        LOGGER.debug(String.format("Save document \n %s", emplView.toString()));
        Document documentEntity = null;
        DocumentType docType = documentTypeService.getByName(emplView.getDocName());
        if (docType != null) {
            try {
                documentEntity = documentRepository.findByNumber(emplView.getDocNumber());
                if (documentEntity == null) {
                    documentEntity = new Document();
                }
                Document docView = mapperFacade.mapToDocument(emplView, new Document());
                mapperFacade.map(docView, documentEntity);
                documentEntity.setDocumentType(docType);
                documentRepository.saveAndFlush(documentEntity);
            } catch (Exception ex) {
                throw new CantSaveNewObject("document", ex);
            }
        }
        return documentEntity;
    }
}
