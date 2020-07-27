package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.entities.Detail;
import com.hcb.hotchairs.entities.Reservation;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Objects;

@Component
public class DetailConverter {

    public DetailDTO toDTO(Detail detail) {

        DetailDTO detailDTO = new DetailDTO();
        detailDTO.setId(detail.getId());
        detailDTO.setReservationId(detail.getReservation().getId());
        detailDTO.setDate(detail.getDate());

        return detailDTO;
    }

    public Detail fromDTO(Date date, Long reservationId){
        if( Objects.isNull(date) || Objects.isNull(reservationId)) {
            return null;
        }

        Detail detail = new Detail();
        detail.setDate(date);
        detail.setReservation(new Reservation());
        detail.getReservation().setId(reservationId);

        return detail;
    }
}
