package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.entities.Reservation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ReservationConverter {

    public ReservationDTO toDTO(Reservation reservation) {

        if (Objects.isNull(reservation)) {
            return null;
        }

        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setHostId(reservation.getHost().getId());
        reservationDTO.setUserId(reservation.getUser().getId());
        reservationDTO.setPlaceId(reservation.getPlace().getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        return reservationDTO;
    }
}
