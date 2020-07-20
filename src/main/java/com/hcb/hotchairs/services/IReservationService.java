package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationDTO;

import java.util.List;

public interface IReservationService {
    List<ReservationDTO> getAll();
    ReservationDTO getById(Long id);
}
