package com.hcb.hotchairs.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO implements GrantedAuthority {

    private Long id;
    private String name;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
