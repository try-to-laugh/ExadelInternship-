package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.PlaceConverter;
import com.hcb.hotchairs.daos.IPlaceDAO;
import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.entities.Place;
import com.hcb.hotchairs.services.IPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PlaceDTO getById(Long id){
        return placeConverter.toDTO(placeDAO.findById(id).orElse(null));
    }
}
