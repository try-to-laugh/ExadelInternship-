package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.FloorConverter;
import com.hcb.hotchairs.daos.IFloorDAO;
import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.entities.Floor;
import com.hcb.hotchairs.services.IFloorService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<FloorDTO> getAllByOfficeId(Long id){
        return floorDAO.findAllByOfficeId(id).stream().map(floorConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public FloorDTO getById(Long id){
        return floorConverter.toDTO(floorDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional
    @Modifying
    public void saveBatch(List<FloorDTO> floors) {
        floorDAO.saveAll(floors.stream().map(floorConverter::fromDTO).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    @Modifying
    public void save(FloorDTO floorDTO) {
        floorDAO.save(floorConverter.fromDTO(floorDTO));
    }
}
