package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.CityDTO;

import java.util.List;

public interface ICityService {
    List<CityDTO> getAll();
    List<CityDTO> getAllByCountryId(Long countryId);
    CityDTO getById(Long id);
}
