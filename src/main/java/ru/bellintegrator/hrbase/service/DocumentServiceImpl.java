package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.repository.DocumentRepository;

/**
 * {@inheritDoc}
 */

@Service
public class DocumentServiceImpl implements GenericGetByParamService<Document> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentServiceImpl.class.getName());

    @Autowired
    private DocumentRepository documentRepository;

    /**
     * {@inheritDoc}
     */
    public Document getByCode(String number) {
        Document document = documentRepository.findByNumber(number);
        if (number != null && !number.isEmpty() && document == null) {
            throw new CantFindByParam(String.format("docCode=%s", number));
        }
        LOGGER.debug(String.format("Find document by number=%s \n result: %s", number, document));
        return document;
    }
}
