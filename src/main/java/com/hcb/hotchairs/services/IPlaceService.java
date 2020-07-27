package com.hcb.hotchairs.services;



import com.hcb.hotchairs.dtos.PlaceDTO;

import java.util.Collection;
import java.util.List;

public interface IPlaceService {

    List<PlaceDTO> getAll();
    List<PlaceDTO> getAllByFloorId(Long floorId);
    List<PlaceDTO> getAllByOfficeId(Long officeId);
    List<PlaceDTO> getFreePlaceOnFloor(Collection<Long> closed, Long floorId);
    List<PlaceDTO> getFreePlaceInOffice(Collection<Long> closed, Long officeId);
    PlaceDTO getById(Long id);
}
