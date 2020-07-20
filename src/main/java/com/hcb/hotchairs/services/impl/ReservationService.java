package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;

    @Autowired
    ReservationService(IReservationDAO reservationDAO, ReservationConverter reservationConverter) {
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationDAO.findAll().stream().map(reservationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getById(Long id) {
        return reservationConverter.toDTO(reservationDAO.findById(id).orElse(null));
    }
}
