package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.OfficeConverter;
import com.hcb.hotchairs.daos.IOfficeDAO;
import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.entities.Office;
import com.hcb.hotchairs.services.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OfficeService implements IOfficeService {

    private final IOfficeDAO officeDAO;
    private final OfficeConverter officeConverter;

    @Autowired
    public OfficeService(IOfficeDAO officeDAO, OfficeConverter officeConverter){
        this.officeDAO = officeDAO;
        this.officeConverter = officeConverter;
    }

    @Override
    public List<OfficeDTO> getAll(){
        return officeDAO.findAll().stream().map(officeConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OfficeDTO> getAllByCityId(Long cityId){
        return officeDAO.findAllByCityId(cityId).stream().map(officeConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public OfficeDTO getById(Long id){
        return officeConverter.toDTO(officeDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional
    @Modifying
    public void saveOffice(OfficeDTO newOffice) {
        officeDAO.save(officeConverter.fromDTO(newOffice));
    }
}
