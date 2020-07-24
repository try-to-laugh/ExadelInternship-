package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.OfficeConverter;
import com.hcb.hotchairs.daos.IOfficeDAO;
import com.hcb.hotchairs.dtos.OfficeDTO;
import com.hcb.hotchairs.entities.Office;
import com.hcb.hotchairs.services.IOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService implements IOfficeService {

    private final IOfficeDAO officeDAO;
    private final OfficeConverter officeConverter;

    @Autowired
    public OfficeService(IOfficeDAO officeDAO, OfficeConverter officeConverter) {
        this.officeDAO = officeDAO;
        this.officeConverter = officeConverter;
    }

    @Override
    public List<OfficeDTO> getAll() {
        return officeDAO.findAll().stream().map(officeConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OfficeDTO> getAllByCityId(Long cityId) {
        return officeDAO.findAllByCityId(cityId).stream().map(officeConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public OfficeDTO getById(Long id){
        return officeConverter.toDTO(officeDAO.findById(id).orElse(null));
    }

    @Override
    @Transactional
    @Modifying
    public OfficeDTO saveOffice(OfficeDTO newOffice) {
        return officeConverter.toDTO(officeDAO.save(officeConverter.fromDTO(newOffice)));
    }

    @Override
    public List<OfficeDTO> getPagedAndSorted(Integer pageNumber, Integer pageSize, String sortMethod) {
        Sort sortConfig;

        switch (sortMethod) {
            case "city":
                sortConfig = Sort.by(Sort.Direction.ASC, "City.name");
                break;

            case "country":
                sortConfig = Sort.by(Sort.Direction.ASC, "City.Country.name");
                break;

            default:
                sortConfig = Sort.by(Sort.Direction.DESC, "Id");
                break;
        }

        Pageable pageConfig = PageRequest.of(pageNumber, pageSize, sortConfig);
        Page<Office> officePage = officeDAO.findAll(pageConfig);

        return officePage.get().map(officeConverter::toDTO).collect(Collectors.toList());
    }
}
