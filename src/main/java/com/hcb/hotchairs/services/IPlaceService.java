package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.PlaceDTO;

import java.util.List;

public interface IPlaceService {
    List<PlaceDTO> getAll();
    PlaceDTO getById(Long id);
}
