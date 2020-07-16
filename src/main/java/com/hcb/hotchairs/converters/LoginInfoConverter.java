package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.LoginInfoDTO;
import com.hcb.hotchairs.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LoginInfoConverter {

    private final RoleConverter roleConverter;

    @Autowired
    public LoginInfoConverter(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }

    public LoginInfoDTO toDTO(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        LoginInfoDTO loginInfoDTO = new LoginInfoDTO();
        loginInfoDTO.setId(user.getId());
        loginInfoDTO.setUsername(user.getEmail());
        loginInfoDTO.setPassword(user.getPassword());
        loginInfoDTO.setAuthorities(user.getRoles().stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList()));

        loginInfoDTO.setIsAccountNonExpired(true);
        loginInfoDTO.setIsAccountNonLocked(true);
        loginInfoDTO.setIsCredentialsNonExpired(true);
        loginInfoDTO.setIsEnabled(true);

        return loginInfoDTO;
    }

    @Deprecated
    public User fromDTO(LoginInfoDTO loginInfoDTO) {
        return null;
    }
}
