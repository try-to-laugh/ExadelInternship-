package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.CityDTO;
import com.hcb.hotchairs.dtos.CountryDTO;
import com.hcb.hotchairs.entities.Country;

import java.util.List;

public interface ICountryService {
    List<CountryDTO> getAll();
    CountryDTO getById(Long id);
    List<CityDTO> getCountryCities(Long id);
}
