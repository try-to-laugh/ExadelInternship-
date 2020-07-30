package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.entities.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class RoleConverter {

    public Role fromDTO(RoleDTO roleDTO) {
        if (Objects.isNull(roleDTO)) {
            return null;
        }

        return new Role(roleDTO.getId(), roleDTO.getName(), new ArrayList<>(), new ArrayList<>());
    }

    public RoleDTO toDTO(Role role) {
        if (Objects.isNull(role)) {
            return null;
        }

        return new RoleDTO(role.getId(), role.getName());
    }
}
