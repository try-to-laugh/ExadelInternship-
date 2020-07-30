package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.entities.Detail;

import java.util.List;

public interface IDetailService {
    List<DetailDTO> getAll();
    DetailDTO getById(Long id);
    DetailDTO saveDetail(Detail detail);
    List<DetailDTO> getActiveByReservationId(Long reservationId);
}
