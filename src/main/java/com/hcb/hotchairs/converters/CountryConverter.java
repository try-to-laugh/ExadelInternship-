package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.CountryDTO;
import com.hcb.hotchairs.entities.Country;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CountryConverter {

    public CountryDTO toDTO(Country country){
        if(Objects.isNull(country)){
            return null;
        }
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setName(country.getName());
        return countryDTO;
    }
}
