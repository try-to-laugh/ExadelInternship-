package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO getByEmail(String email);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    List<ReservationDTO> getUserReservations(Long id);
    List<DetailDTO> getUserDetails(Long id);
}
