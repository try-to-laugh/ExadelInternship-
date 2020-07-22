package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.entities.Floor;
import com.hcb.hotchairs.entities.Office;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class FloorConverter {
    public FloorDTO toDTO(Floor floor){
        if (Objects.isNull(floor)){
            return null;
        }

        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setId(floor.getId());
        floorDTO.setNumber(floor.getNumber());
        floorDTO.setOfficeId(floor.getOffice().getId());
        return floorDTO;
    }

    public Floor fromDTO(FloorDTO floorDTO) {
        if (Objects.isNull(floorDTO)) {
            return null;
        }

        Floor floor = new Floor();
        floor.setId(floorDTO.getId());
        floor.setNumber(floorDTO.getNumber());
        floor.setPlaces(new ArrayList<>());
        floor.setOffice(new Office());
        floor.getOffice().setId(floorDTO.getOfficeId());

        return floor;
    }
}
