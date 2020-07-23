package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;

import java.util.List;

public interface IUserService {

    UserDTO getByEmail(String email);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    List<ReservationDTO> getUserReservations(Long id);
    //ReservationDTO getNearestUserReservations(Long id);
}
