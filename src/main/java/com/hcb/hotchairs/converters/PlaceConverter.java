package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.entities.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class PlaceConverter {

    private final RoleConverter roleConverter;
    private final TagConverter tagConverter;

    @Autowired
    public PlaceConverter(RoleConverter roleConverter,TagConverter tagConverter){
        this.roleConverter = roleConverter;
        this.tagConverter = tagConverter;
    }

    public PlaceDTO toDTO(Place place){
        if(Objects.isNull(place)){
            return null;
        }
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setCapacity(place.getCapacity());
        placeDTO.setName(place.getName());
        placeDTO.setId(place.getId());
        placeDTO.setFloorId(place.getFloor().getId());

        if(!Objects.isNull(place.getRoles())){
            placeDTO.setRoles(place.getRoles().stream().map(roleConverter::toDTO).collect(Collectors.toList()));
        }

        if(!Objects.isNull(place.getTags())){
            placeDTO.setTags(place.getTags().stream().map(tagConverter::toDTO).collect(Collectors.toList()));
        }

        return placeDTO;
    }
}
