package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.OfficeConverter;
import com.hcb.hotchairs.daos.IOfficeDAO;
import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.services.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<OfficeDTO> getAll(){
        return officeDAO.findAll().stream().map(officeConverter::toDTO).collect(Collectors.toList());
    }

    public OfficeDTO getById(Long id){
        return officeConverter.toDTO(officeDAO.findById(id).orElse(null));
    }
}
