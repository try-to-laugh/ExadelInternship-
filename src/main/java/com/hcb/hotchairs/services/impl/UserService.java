package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.converters.UserConverter;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.daos.IUserDAO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;
    private final UserConverter userConverter;
    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;

    @Autowired
    public UserService(IUserDAO userDAO, UserConverter userConverter,
                       IReservationDAO reservationDAO, ReservationConverter reservationConverter) {
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
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
        return reservationDAO.findAllByUserId(id).stream().map(reservationConverter::toDTO).collect(Collectors.toList());
    }
}
