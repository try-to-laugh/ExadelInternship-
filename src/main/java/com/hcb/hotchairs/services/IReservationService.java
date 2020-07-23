package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.entities.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public interface IReservationService {

    List<ReservationDTO> getAll();
    List<ReservationDTO> getAllByTimeDateAndFloor(Date date, Time startTime, Time endTime, Long floorId);
    List<ReservationDTO> getAllByTimeDateAndOffice(Date date, Time startTime, Time endTime, Long officeId);
    ReservationDTO getById(Long id);
}
