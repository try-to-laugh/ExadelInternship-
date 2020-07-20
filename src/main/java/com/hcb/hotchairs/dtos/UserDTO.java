package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Long hrId;
    private List<RoleDTO> roles;

    /* TODO:
        1. Annotation-based validation, with almost all DTO objects.
     */
}
