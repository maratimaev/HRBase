package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.repository.DocumentTypeRepository;
import ru.bellintegrator.hrbase.view.doctype.DocTypeViewSave;

/**
 * {@inheritDoc}
 */

@Service
public class DocumentTypeServiceImpl implements GenericGetByNameService<DocumentType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeServiceImpl.class.getName());

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Autowired
    private MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    public DocumentType getByCode(String code) {
        DocumentType documentType = documentTypeRepository.findByCode(code);
        if (code != null && !code.isEmpty() && documentType == null) {
            throw new CantFindByParam(String.format("docCode=%s", code));
        }
        LOGGER.debug(String.format("Find documentType by code=%s \n result: %s", code, documentType));
        return documentType;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentType getByName(String name) {
        DocumentType documentType = documentTypeRepository.findByName(name);
        if (name != null && !name.isEmpty() && documentType == null) {
            throw new CantFindByParam(String.format("docName=%s", name));
        }
        LOGGER.debug(String.format("Find documentType by name=%s \n result: %s", name, documentType));
        return documentType;
    }

    /** Сохранение типа документа
     * @param docTypeViewSave типа документа
     */
    public void save(DocTypeViewSave docTypeViewSave) {
        LOGGER.debug(String.format("Save docType \n %s", docTypeViewSave.toString()));
        try {
            documentTypeRepository.saveAndFlush(mapperFacade.map(docTypeViewSave, DocumentType.class));
        } catch (Exception ex) {
            throw new CantSaveNewObject("docType");
        }
    }
}
