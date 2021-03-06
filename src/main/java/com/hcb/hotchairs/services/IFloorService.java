package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.FloorDTO;
import java.util.List;

public interface IFloorService {
    List<FloorDTO> getAll();
    List<FloorDTO> getAllByOfficeId(Long id);
    FloorDTO getById(Long id);
    List<FloorDTO> saveBatch(List<FloorDTO> floors);
    FloorDTO save(FloorDTO floorDTO);
    boolean deleteById(Long id);

    byte[] getFloorMap(Long id);
    boolean setFloorMap(byte[] svg, Long id);
    boolean deleteFloorMap(Long id);

    byte[] getFloorPlan(Long id);
    boolean setFloorPlan(byte[] png, Long id);
    boolean deleteFloorPlan(Long id);
}
