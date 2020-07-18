package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.entities.Office;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OfficeConverter {
    public OfficeDTO toDTO(Office office){
        if(Objects.isNull(office)){
            return null;
        }
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setId(office.getId());
        officeDTO.setCityId(office.getCity().getId());
        officeDTO.setStreet(office.getStreet());

        return officeDTO;
    }
}
