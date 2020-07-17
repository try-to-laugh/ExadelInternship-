package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter {

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
        userDTO.setHrId(user.getHr().getId());

        return userDTO;
    }
}
