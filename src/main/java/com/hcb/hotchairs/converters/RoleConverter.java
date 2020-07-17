package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.entities.Role;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleConverter {

    public Role fromDTO(RoleDTO roleDTO) {
        if (Objects.isNull(roleDTO)) {
            return null;
        }

        Role role = new Role();
        role.setId(roleDTO.getId());
        role.setName(roleDTO.getName());

        return role;
    }

    public RoleDTO toDTO(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }

        return new RoleDTO(role.getId(), role.getName());
    }
}
