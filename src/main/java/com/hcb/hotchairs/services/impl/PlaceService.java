package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.PlaceConverter;
import com.hcb.hotchairs.daos.IPlaceDAO;
import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.entities.Place;
import com.hcb.hotchairs.services.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaceService implements IPlaceService {

    private final IPlaceDAO placeDAO;
    private final PlaceConverter placeConverter;

    @Autowired
    public PlaceService(IPlaceDAO placeDAO, PlaceConverter placeConverter){
        this.placeDAO = placeDAO;
        this.placeConverter = placeConverter;
    }

    @Override
    public  List<PlaceDTO> getAll(){
        return placeDAO.findAll().stream().map(placeConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<PlaceDTO> getAllByFloorId(Long floorId) {
        return placeDAO.findAllByFloorId(floorId)
                .stream()
                .map(placeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaceDTO> getAllByOfficeId(Long officeId) {
        return placeDAO.findAllByOfficeId(officeId)
                .stream()
                .map(placeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaceDTO> getFreePlaceOnFloor(Collection<Long> closed, Long floorId) {
       return  placeDAO.findFreePlaceOnFloor(closed, floorId)
                .stream()
                .map(placeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlaceDTO> getFreePlaceInOffice(Collection<Long> closed, Long officeId) {
       return placeDAO.findFreePlaceOnOffice(closed, officeId)
                .stream()
                .map(placeConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlaceDTO getById(Long id){
        return placeConverter.toDTO(placeDAO.findById(id).orElse(null));
    }

    @Override
    @Modifying
    @Transactional
    public List<PlaceDTO> savePlaces(List<PlaceDTO> places) {
        return placeDAO.saveAll(places.stream().map(placeConverter::fromDTO).collect(Collectors.toList()))
                .stream()
                .map(placeConverter::toDTO)
                .collect(Collectors.toList());
    }
}
