package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.entities.City;
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

    public Office fromDTO(OfficeDTO officeDTO) {
        if (Objects.isNull(officeDTO)) {
            return null;
        }

        Office office = new Office();
        office.setId(officeDTO.getId());
        office.setStreet(officeDTO.getStreet());
        office.setCity(new City());
        office.getCity().setId(officeDTO.getCityId());

        return office;
    }
}
