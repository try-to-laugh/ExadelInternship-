package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.CountryConverter;
import com.hcb.hotchairs.daos.ICountryDAO;
import com.hcb.hotchairs.dtos.CountryDTO;
import com.hcb.hotchairs.entities.Country;
import com.hcb.hotchairs.services.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService implements ICountryService {

    private final ICountryDAO countryDAO;
    private final CountryConverter countryConverter;

    @Autowired
    public CountryService(ICountryDAO countryDAO, CountryConverter countryConverter) {
        this.countryDAO = countryDAO;
        this.countryConverter = countryConverter;
    }

    @Override
    public List<CountryDTO> getAll() {
        return countryDAO.findAll().stream().map(countryConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public CountryDTO getById(Long id){
        return countryConverter.toDTO(countryDAO.findById(id).orElse(null));
    }
}
