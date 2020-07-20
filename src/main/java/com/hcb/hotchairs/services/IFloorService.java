package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.FloorDTO;
import java.util.List;

public interface IFloorService {
    List<FloorDTO> getAll();
    List<FloorDTO> getAllByOfficeId(Long id);
    FloorDTO getById(Long id);
}
