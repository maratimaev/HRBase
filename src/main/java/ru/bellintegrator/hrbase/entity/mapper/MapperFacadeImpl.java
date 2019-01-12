package ru.bellintegrator.hrbase.entity.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.Document;
import ru.bellintegrator.hrbase.entity.DocumentType;
import ru.bellintegrator.hrbase.entity.Employer;
import ru.bellintegrator.hrbase.entity.Office;
import ru.bellintegrator.hrbase.view.EmployerView;

import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class MapperFacadeImpl implements MapperFacade {
    private final MapperFactory mapperFactory;


    @Autowired
    public MapperFacadeImpl(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
        mapperFactory.getConverterFactory().registerConverter("DateToStringConverter", new DateToStringConverter("yyyy-MM-dd"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().map(sourceObject, destinationClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> void map(S sourceObject, D destinationObject) {
        mapperFactory.getMapperFacade().map(sourceObject, destinationObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().mapAsList(source, destinationClass);
    }

    @Override
    public Document mapToDocument(EmployerView employerView, Document document) {
        mapperFactory.classMap(EmployerView.class, Document.class)
                .field("docNumber", "number")
                .fieldMap("docDate", "date")
                .converter("DateToStringConverter").add()
                .register();
        mapperFactory.getMapperFacade(EmployerView.class, Document.class).map(employerView, document);
        return document;
    }

    public EmployerView mapToEmployer(Employer employer, EmployerView employerView) {
        mapperFactory.classMap(Document.class, EmployerView.class)
                .field("number", "docNumber")
                .fieldMap("date", "docDate")
                .converter("DateToStringConverter").add()
                .register();
        mapperFactory.classMap(DocumentType.class, EmployerView.class)
                .field("code", "docCode")
                .field("name", "docName")
                .register();
        mapperFactory.classMap(Country.class, EmployerView.class)
                .field("code", "citizenshipCode")
                .field("name", "citizenshipName")
                .register();
        mapperFactory.classMap(Office.class, EmployerView.class)
                .field("id", "officeId")
                .register();
        mapperFactory.getMapperFacade(Employer.class, EmployerView.class).map(employer, employerView);
        mapperFactory.getMapperFacade(Document.class, EmployerView.class).map(employer.getDocument(), employerView);
        mapperFactory.getMapperFacade(DocumentType.class, EmployerView.class).map(employer.getDocument().getDocumentType(), employerView);
        mapperFactory.getMapperFacade(Country.class, EmployerView.class).map(employer.getCitizenship(), employerView);
        mapperFactory.getMapperFacade(Office.class, EmployerView.class).map(employer.getOffice(), employerView);
        return employerView;
    }
}
