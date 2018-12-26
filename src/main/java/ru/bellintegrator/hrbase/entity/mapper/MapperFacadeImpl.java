package ru.bellintegrator.hrbase.entity.mapper;

import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

//    public EmployerView mapToEmployerView(Employer employer, Document document, EmployerView employerView) {
//        mapperFactory.classMap(Employer.class, EmployerView.class)
//                .field("firstName", "firstName").register();
//        mapperFactory.classMap(Document.class, EmployerView.class)
//                .field("number", "docNumber").register();
//
//        mapperFactory.getMapperFacade(Employer.class, EmployerView.class).map(employer, employerView);
//        mapperFactory.getMapperFacade(Document.class, EmployerView.class).map(document, employerView);
//
//        return employerView;
//    }
}
