package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.FloorConverter;
import com.hcb.hotchairs.daos.IFloorDAO;
import com.hcb.hotchairs.dtos.FloorDTO;
import com.hcb.hotchairs.entities.Floor;
import com.hcb.hotchairs.entities.Office;
import com.hcb.hotchairs.services.IFloorService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
    public List<FloorDTO> saveBatch(List<FloorDTO> floors) {
        return floorDAO.saveAll(floors.stream().map(floorConverter::fromDTO).collect(Collectors.toList()))
                .stream()
                .map(floorConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @Modifying
    public FloorDTO save(FloorDTO floorDTO) {
        return floorConverter.toDTO(floorDAO.saveAndFlush(floorConverter.fromDTO(floorDTO)));
    }

    @Override
    public byte[] getFloorSvg(Long id) {
        return floorDAO.findById(id).map(Floor::getSvg).orElse(null);
    }

    @Override
    @Modifying
    @Transactional
    public boolean setFloorSvg(byte[] svg, Long id) {
        Optional<Floor> floor = floorDAO.findById(id);
        if (floor.isPresent()) {
            floor.get().setSvg(svg);
            return true;
        }

        return false;
    }

    @Override
    @Modifying
    @Transactional
    public boolean deleteFloorSvg(Long id) {
        Optional<Floor> floor = floorDAO.findById(id);
        if (floor.isPresent()) {
            floor.get().setSvg(null);
            return true;
        }

        return false;
    }
}
