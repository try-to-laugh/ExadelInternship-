package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.OfficeDTO;

import java.util.List;

public interface IOfficeService {
    List<OfficeDTO> getAll();
    OfficeDTO getById(Long id);
}
