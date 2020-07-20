package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.entities.Floor;
import org.springframework.objenesis.ObjenesisException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FloorConverter {
    public FloorDTO toDTO(Floor floor){
        if(Objects.isNull(floor)){
            return null;
        }

        FloorDTO floorDTO = new FloorDTO();
        floorDTO.setId(floor.getId());
        floorDTO.setName(floor.getName());
        floorDTO.setOfficeId(floor.getOffice().getId());
        return floorDTO;
    }
}
