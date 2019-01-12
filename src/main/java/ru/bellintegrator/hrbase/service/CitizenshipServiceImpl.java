package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.entity.mapper.MapperFacade;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.exception.CantSaveNewObject;
import ru.bellintegrator.hrbase.repository.CitizenshipRepository;
import ru.bellintegrator.hrbase.view.CountryView;

/**
 * {@inheritDoc}
 */
@Service
public class CitizenshipServiceImpl implements GenericGetByParamService<Country> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CitizenshipServiceImpl.class.getName());

    private final CitizenshipRepository citizenshipRepository;

    private final MapperFacade mapperFacade;

    @Autowired
    public CitizenshipServiceImpl(CitizenshipRepository citizenshipRepository, MapperFacade mapperFacade) {
        this.citizenshipRepository = citizenshipRepository;
        this.mapperFacade = mapperFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    public Country getByCode(String code) {
        Country country = citizenshipRepository.findByCode(code);
        if (code != null && !code.isEmpty() && country == null) {
            throw new CantFindByParam(String.format("citizenshipCode=%s", code));
        }
        LOGGER.debug(String.format("Find country by code=%s \n result: %s", code, country));
        return country;
    }

    /** Сохранение страны в БД
     * @param countryView страны
     */
    @Transactional
    public void save(CountryView countryView) {
        LOGGER.debug(String.format("Save country \n %s", countryView.toString()));
        try {
            citizenshipRepository.saveAndFlush(mapperFacade.map(countryView, Country.class));
        } catch (Exception ex) {
            throw new CantSaveNewObject("country");
        }
    }
}
