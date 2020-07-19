package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.entities.Role;

import java.util.List;

public interface IRoleService {

    RoleDTO getRoleById(Long id);
    List<RoleDTO> getAll();
}
