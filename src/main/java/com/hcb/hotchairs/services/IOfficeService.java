package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.OfficeDTO;

import java.util.List;

public interface IOfficeService {
    List<OfficeDTO> getAll();
    List<OfficeDTO> getAllByCityId(Long cityId);
    OfficeDTO getById(Long id);
    OfficeDTO saveOffice(OfficeDTO newOffice);
    List<OfficeDTO> getPagedAndSorted(Integer pageNumber, Integer pageSize, String sortMethod);
}
