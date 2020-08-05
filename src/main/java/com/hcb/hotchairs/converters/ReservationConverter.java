package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.entities.Place;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.entities.User;
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
        reservationDTO.setHostId(Objects.isNull((reservation.getHost())) ? reservation.getId()
                : reservation.getHost().getId());
        reservationDTO.setUserId(reservation.getUser().getId());
        reservationDTO.setPlaceId(reservation.getPlace().getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setWeekDays(reservation.getWeekDays());
        return reservationDTO;
    }


    public Reservation fromDTO(ReservationInfoDTO reservationInfoDTO, Long hostId){
        if (Objects.isNull(reservationInfoDTO)) {
            return null;
        }

        Reservation reservation = new Reservation();
        Reservation hostReservation = null;
        if (!Objects.isNull(hostId)) {
            hostReservation = new Reservation();
            hostReservation.setId(hostId);
        }

        reservation.setHost(hostReservation);
        reservation.setStartDate(reservationInfoDTO.getStartDate());
        reservation.setEndDate(reservationInfoDTO.getEndDate());
        reservation.setStartTime(reservationInfoDTO.getStartTime());
        reservation.setEndTime(reservationInfoDTO.getEndTime());
        reservation.setPlace(new Place());
        reservation.getPlace().setId(reservationInfoDTO.getPlaceId());
        reservation.setUser(new User());
        reservation.getUser().setId(reservationInfoDTO.getCurrentUserId());
        reservation.setWeekDays(reservationInfoDTO.getWeekDay());

        return reservation;
    }

    public Reservation fromDTO(ReservationDTO reservationDTO){
        if(Objects.isNull(reservationDTO)){
            return null;
        }
        Reservation reservation  = new Reservation();

        if(!Objects.isNull(reservationDTO.getHostId())) {
            reservation.setHost(new Reservation());
            reservation.getHost().setId(reservationDTO.getHostId());
        }

        reservation.setUser(new User());
        reservation.getUser().setId(reservationDTO.getUserId());

        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());

        reservation.setWeekDays(reservationDTO.getWeekDays());

        reservation.setPlace(new Place());
        reservation.getPlace().setId(reservationDTO.getPlaceId());

        reservation.setId(reservationDTO.getId());
        return reservation;
    }
}
