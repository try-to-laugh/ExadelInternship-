package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.daos.IDetailDAO;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;
    private final IDetailDAO detailDAO;
    private final DetailConverter detailConverter;

    @Autowired
    ReservationService(IReservationDAO reservationDAO, ReservationConverter reservationConverter,
                       IDetailDAO detailDAO, DetailConverter detailConverter) {
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
        this.detailDAO = detailDAO;
        this.detailConverter = detailConverter;
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationDAO.findAll().stream().map(reservationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllByTimeDateAndFloor(Date date, Time startTime, Time endTime, Long floorId) {
        return reservationDAO.findAllByTimeDateAndFloor(date, startTime, endTime, floorId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllByTimeDateAndOffice(Date date, Time startTime, Time endTime, Long officeId) {
        return reservationDAO.findAllByTimeDateAndOffice(date, startTime, endTime, officeId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getByTimeDateAndPlace(Date date, Time startTime, Time endTime, Long placeId) {
        return reservationConverter.toDTO(reservationDAO.findByTimeDateAndPlace(date, startTime, endTime, placeId)
                .orElse(null));

    }

    @Override
    @Modifying
    public ReservationDTO saveReservation(Reservation reservation) {
        return reservationConverter.toDTO(reservationDAO.save(reservation));
    }

    @Override
    public ReservationDTO getById(Long id) {
        return reservationConverter.toDTO(reservationDAO.findById(id).orElse(null));
    }

    @Override
    public List<DetailDTO> getReservationDetails(Long id) {
        return detailDAO.findByReservationId(id).stream()
                .map(detailConverter::toDTO)
                .collect(Collectors.toList());
    }
}
