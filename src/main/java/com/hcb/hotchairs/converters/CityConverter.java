package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.CityDTO;
import com.hcb.hotchairs.entities.City;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CityConverter {

    public CityDTO toDTO(City city){
        if(Objects.isNull(city)){
            return null;
        }

        CityDTO cityDTO = new CityDTO();
        cityDTO.setCountryId(city.getCountry().getId());
        cityDTO.setId(city.getId());
        cityDTO.setName(city.getName());
        return cityDTO;
    }
}
