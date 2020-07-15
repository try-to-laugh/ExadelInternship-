package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Long hrId;

    /* TODO:
        > Validation annotations.
     */
}
