package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.CityConverter;
import com.hcb.hotchairs.converters.CountryConverter;
import com.hcb.hotchairs.daos.ICityDAO;
import com.hcb.hotchairs.daos.ICountryDAO;
import com.hcb.hotchairs.dtos.CityDTO;
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
    private final ICityDAO cityDAO;
    private final CountryConverter countryConverter;
    private final CityConverter cityConverter;

    @Autowired
    public CountryService(ICountryDAO countryDAO, CountryConverter countryConverter,
                          ICityDAO cityDAO, CityConverter cityConverter) {
        this.countryDAO = countryDAO;
        this.countryConverter = countryConverter;
        this.cityDAO = cityDAO;
        this.cityConverter = cityConverter;
    }

    @Override
    public List<CountryDTO> getAll() {
        return countryDAO.findAll().stream().map(countryConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public CountryDTO getById(Long id){
        return countryConverter.toDTO(countryDAO.findById(id).orElse(null));
    }

    @Override
    public List<CityDTO> getCountryCities(Long id) {
        return cityDAO.findAllByCountryId(id).stream().map(cityConverter::toDTO).collect(Collectors.toList());
    }
}
