package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.entities.Detail;
import org.springframework.stereotype.Component;

@Component
public class DetailConverter {

    public DetailDTO toDTO(Detail detail) {

        DetailDTO detailDTO = new DetailDTO();
        detailDTO.setId(detail.getId());
        detailDTO.setReservationId(detail.getReservation().getId());
        detailDTO.setDate(detail.getDate());

        return detailDTO;
    }
}
