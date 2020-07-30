package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.converters.UserConverter;
import com.hcb.hotchairs.daos.IDetailDAO;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.daos.IUserDAO;
import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;
    private final UserConverter userConverter;
    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;
    private final IDetailDAO detailDAO;
    private final DetailConverter detailConverter;

    @Autowired
    public UserService(IUserDAO userDAO, UserConverter userConverter,
                       IReservationDAO reservationDAO, ReservationConverter reservationConverter,
                       IDetailDAO detailDAO, DetailConverter detailConverter) {
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
        this.detailDAO = detailDAO;
        this.detailConverter=detailConverter;
    }

    @Override
    public UserDTO getByEmail(String email) {
        return userConverter.toDTO(userDAO.findByEmail(email));
    }

    @Override
    public UserDTO getById(Long id) {
        return userConverter.toDTO(userDAO.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAll() {
        return userDAO.findAll().stream().map(userConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getUserReservations(Long id) {
        return reservationDAO.findByUserId(id).stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DetailDTO> getUserDetails(Long id) {
        return detailDAO.findByUserId(id).stream().map(detailConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getPagedAndSorted(Integer pageNumber, Integer pageSize, String sortMethod, String sortDirection) {
        Sort sortConfig;

        if ("name".equals(sortMethod)) {
            sortConfig = Sort.by(sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
        } else {
            sortConfig = Sort.by(sortDirection.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, "id");
        }

        Pageable pageConfig = PageRequest.of(pageNumber, pageSize, sortConfig);
        return userDAO.findAll(pageConfig).get().map(userConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public Long getUsersCount() {
        return userDAO.count();
    }
}
