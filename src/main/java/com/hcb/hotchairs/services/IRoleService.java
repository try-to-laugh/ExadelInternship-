package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.entities.Role;

import java.util.Collection;
import java.util.List;

public interface IRoleService {
    RoleDTO getRoleById(Long id);
    List<RoleDTO> getAllByIdCollection(Collection<Long> rolesId);
    List<RoleDTO> getAll();
}
