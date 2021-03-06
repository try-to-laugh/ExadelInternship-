package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.daos.IDetailDAO;
import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.entities.Detail;
import com.hcb.hotchairs.services.IDetailService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetailService implements IDetailService {

    private final IDetailDAO detailDAO;
    private final DetailConverter detailConverter;

    DetailService(IDetailDAO detailDAO, DetailConverter detailConverter) {
        this.detailDAO = detailDAO;
        this.detailConverter = detailConverter;
    }

    @Override
    public List<DetailDTO> getAll() {
        return detailDAO.findAll().stream().map(detailConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public DetailDTO getById(Long id) {
        return detailConverter.toDTO(detailDAO.findById(id).orElse(null));
    }

    @Override
    @Modifying
    public DetailDTO saveDetail(Detail detail) {
        return detailConverter.toDTO(detailDAO.save(detail));
    }

    @Override
    public List<DetailDTO> getActiveByReservationId(Long reservationId) {
        return detailDAO.findByReservationId(reservationId)
                .stream()
                .map(detailConverter::toDTO)
                .collect(Collectors.toList());
    }
}