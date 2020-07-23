package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.controllers.RoleController;
import com.hcb.hotchairs.converters.RoleConverter;
import com.hcb.hotchairs.daos.IRoleDAO;
import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.entities.Role;
import com.hcb.hotchairs.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleService implements IRoleService {

    private final IRoleDAO roleDAO;
    private final RoleConverter roleConverter;

    @Autowired
    public RoleService(IRoleDAO roleDAO, RoleConverter roleConverter) {
        this.roleDAO = roleDAO;
        this.roleConverter = roleConverter;
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        return roleConverter.toDTO(roleDAO.findById(id).orElse(null));
    }

    @Override
    public List<RoleDTO> getAllByIdCollection(Collection<Long> rolesId) {
        return roleDAO.findAllFromIdCollection(rolesId)
                .stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleDAO.findAll().stream().map(roleConverter::toDTO).collect(Collectors.toList());
    }
}
