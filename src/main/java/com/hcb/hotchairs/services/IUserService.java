package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;

import java.util.List;

public interface IUserService {

    UserDTO getById(Long id);
    List<UserDTO> getAll();
}
