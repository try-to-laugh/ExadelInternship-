package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public UserConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    public User fromDTO(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setHr(new User());
        user.getHr().setId(userDTO.getHrId());

        if (!Objects.isNull(userDTO.getRoles())) {
            user.setRoles(userDTO.getRoles().stream().map(roleConverter::fromDTO).collect(Collectors.toList()));
        }

        return user;
    }

    public UserDTO toDTO(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());

        if (!Objects.isNull(user.getHr())) {
            userDTO.setHrId(user.getHr().getId());
        }

        if (!Objects.isNull(user.getRoles())) {
            userDTO.setRoles(user.getRoles().stream().map(roleConverter::toDTO).collect(Collectors.toList()));
        }

        return userDTO;
    }
}
