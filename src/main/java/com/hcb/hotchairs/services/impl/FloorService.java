package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.FloorConverter;
import com.hcb.hotchairs.daos.IFloorDAO;
import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.services.IFloorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FloorService implements IFloorService {

    private final IFloorDAO floorDAO;
    private final FloorConverter floorConverter;

    FloorService(IFloorDAO floorDAO, FloorConverter floorConverter){
        this.floorDAO = floorDAO;
        this.floorConverter = floorConverter;
    }

    @Override
    public List<FloorDTO> getAll(){
        return floorDAO.findAll().stream().map(floorConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public FloorDTO getById(Long id){
        return floorConverter.toDTO(floorDAO.findById(id).orElse(null));
    }
}
