package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.repository.DocumentRepository;
import ru.bellintegrator.hrbase.repository.DocumentTypeRepository;

/**
 * {@inheritDoc}
 */
@Service
public class DocumentServiceImpl implements GenericGetByNameService<Document> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class.getName());

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    /**
     * {@inheritDoc}
     */
    public Document getByCode(String number) {
        Document document = documentRepository.findByNumber(number);
        if (number != null && !number.isEmpty() && document == null) {
            throw new CantFindByParam(String.format("docNumber=%s", number));
        }
        LOGGER.debug(String.format("Find document by number=%s \n result: %s", number, document));
        return document;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Document getByName(String name) {
        DocumentType documentType = documentTypeRepository.findByName(name);
        if (name != null && !name.isEmpty() && documentType == null) {
            throw new CantFindByParam(String.format("docType=%s", name));
        }
        LOGGER.debug(String.format("Find documentType by name=%s \n result: %s", name, documentType));

        Document document = documentRepository.findByDocumentType(documentType);
        LOGGER.debug(String.format("Find document by docType=%s \n result: %s", documentType, document));
        return document;
    }
}
