package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.OfficeDTO;

import java.util.List;

public interface IOfficeService {
    List<OfficeDTO> getAll();
    List<OfficeDTO> getAllByCityId(Long cityId);
    OfficeDTO getById(Long id);
    void saveOffice(OfficeDTO newOffice);
}
