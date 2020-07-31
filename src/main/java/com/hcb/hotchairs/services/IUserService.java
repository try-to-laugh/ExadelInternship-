package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.dtos.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO getByEmail(String email);
    UserDTO getById(Long id);
    List<UserDTO> getAll();
    List<ReservationDTO> getUserReservations(Long id);
    List<DetailDTO> getUserDetails(Long id);
    List<UserDTO> getByHrId(Long hrId);
    List<UserDTO> getPagedAndSorted(Integer pageNumber,
                                    Integer pageSize,
                                    String sortMethod,
                                    String sortDirection,
                                    String username);
    Long getUsersCount();
    List<RoleDTO> setUserRoles(List<RoleDTO> roles, Long userId);
    List<UserDTO> getUsersByCredentials(String credentials);
}
