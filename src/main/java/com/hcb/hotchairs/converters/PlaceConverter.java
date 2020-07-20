package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.entities.Place;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PlaceConverter {
    public PlaceDTO toDTO(Place place){
        if(Objects.isNull(place)){
            return null;
        }
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setCapacity(place.getCapacity());
        placeDTO.setName(place.getName());
        placeDTO.setId(place.getId());
        placeDTO.setFlorId(place.getFloor().getId());

        return placeDTO;
    }
}
