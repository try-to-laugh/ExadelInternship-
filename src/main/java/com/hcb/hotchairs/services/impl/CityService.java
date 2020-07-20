package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.CityConverter;
import com.hcb.hotchairs.daos.ICityDAO;
import com.hcb.hotchairs.dtos.CityDTO;
import com.hcb.hotchairs.services.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService implements ICityService {

    private final ICityDAO cityDAO;
    private final CityConverter cityConverter;

    @Autowired
    CityService(ICityDAO cityDAO, CityConverter cityConverter){
        this.cityDAO = cityDAO;
        this.cityConverter =cityConverter;
    }

    @Override
    public List<CityDTO> getAll(){
        return cityDAO.findAll().stream().map(cityConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public CityDTO getById(Long id){
        return cityConverter.toDTO(cityDAO.findById(id).orElse(null));
    }
}
