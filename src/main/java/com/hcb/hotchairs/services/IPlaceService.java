package com.hcb.hotchairs.services;



import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.entities.Place;

import java.util.Collection;
import java.util.List;

public interface IPlaceService {

    List<PlaceDTO> getAll();
    List<PlaceDTO> getAllByFloorId(Long floorId);
    List<PlaceDTO> getAllByOfficeId(Long officeId);
    PlaceDTO getById(Long id);
    List<PlaceDTO> savePlaces(List<PlaceDTO> places, Long floorId);
    Boolean isPlaceEditable(PlaceDTO placeDTO);
}
