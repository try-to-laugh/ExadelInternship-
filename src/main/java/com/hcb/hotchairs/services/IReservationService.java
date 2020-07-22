package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationDTO;

import java.sql.Date;
import java.util.List;

public interface IReservationService {
    List<ReservationDTO> getAll();
    List<ReservationDTO> getAllByDateAndFloor(Date date, Long floorId);
    List<ReservationDTO> getAllByDateAndOffice(Date date, Long officeId);
    ReservationDTO getById(Long id);
}
