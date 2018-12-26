package ru.bellintegrator.hrbase.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.hrbase.entity.Country;
import ru.bellintegrator.hrbase.exception.CantFindByParam;
import ru.bellintegrator.hrbase.repository.CitizenshipRepository;

/**
 * {@inheritDoc}
 */

@Service
public class CitizenshipServiceImpl implements GenericGetByParamService<Country> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CitizenshipServiceImpl.class.getName());

    @Autowired
    private CitizenshipRepository citizenshipRepository;

    /**
     * {@inheritDoc}
     */
    public Country getByCode(String code) {
        Country country = citizenshipRepository.findByCode(code);
        if (code != null && !code.isEmpty() && country == null) {
            throw new CantFindByParam(String.format("citizenshipCode=%s", code));
        }
        LOGGER.debug(String.format("Find country by code=%s \n result: %s", code, country));
        return country;
    }
}
