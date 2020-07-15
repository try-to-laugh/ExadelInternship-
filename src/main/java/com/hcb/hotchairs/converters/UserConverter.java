package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public UserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        TypeMap<User, UserDTO> toDTO =
                modelMapper.createTypeMap(User.class, UserDTO.class);
        TypeMap<UserDTO, User> fromDTO =
                modelMapper.createTypeMap(UserDTO.class, User.class);


        toDTO.addMapping(User::getId, UserDTO::setId);
        toDTO.addMapping(User::getName, UserDTO::setName);
        toDTO.addMapping(User::getEmail, UserDTO::setEmail);
        toDTO.addMapping(user -> user.getHr().getId(), UserDTO::setHrId);

        fromDTO.addMappings(mapper -> {
            mapper.map(UserDTO::getId, User::setId);
            mapper.map(UserDTO::getName, User::setName);
            mapper.map(UserDTO::getEmail, User::setEmail);
            mapper.map(UserDTO::getHrId,
                    (dest, id) -> dest.getHr().setId((Long) id));
        });
    }

    public User fromDTO(UserDTO userDTO) {
        return Objects.isNull(userDTO) ? null : modelMapper.map(userDTO, User.class);
    }

    public UserDTO toDTO(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserDTO.class);
    }
}
