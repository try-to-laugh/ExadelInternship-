package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.DetailDTO;

import java.util.List;

public interface IDetailService {
    List<DetailDTO> getAll();
    DetailDTO getById(Long id);
}
