package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.entities.Reservation;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface IReservationService {

    List<ReservationDTO> getAll();
    List<ReservationDTO> getAllByTimeDateAndFloor(Date date, Time startTime, Time endTime, Long floorId);
    List<ReservationDTO> getAllByTimeDateAndOffice(Date date, Time startTime, Time endTime, Long officeId);
    List<ReservationDTO> getByTimeDateAndPlace(Date date, Time startTime, Time endTime, Long placeId);
    ReservationDTO saveReservation(Reservation reservation);
    ReservationDTO getById(Long id);
    List<DetailDTO> getReservationDetails(Long id);

    List<ReservationDTO> getIntersectionByDateTimeForUser(Date startDate,
                                                          Date endDate,
                                                          Time startTime,
                                                          Time endTime,
                                                          Long userId);

    ReservationDTO deleteById(Long reservationId);
    ReservationDTO getByHostAndUser(Long hostId, Long userId);
    List<ReservationDTO> getAllByHostId(Long hostReservationId);
    List<ReservationDTO> getAllByPlaceInDateTimeRange(Long placeId,
                                                      Date startDate,
                                                      Date endDate,
                                                      Time startTime,
                                                      Time endTime);
}
