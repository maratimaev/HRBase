package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.repository.DocumentTypeRepository;

/**
 * {@inheritDoc}
 */

@Service
public class DocumentTypeServiceImpl implements GenericGetByParamService<DocumentType>, GenericGetByNameService<DocumentType> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeServiceImpl.class.getName());

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

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
}
