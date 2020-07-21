package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class PlaceDTO {
    private Long id;
    private Long capacity;
    private Long florId;
    private String name;
    private List<RoleDTO> roles;
    private List<TagDTO> tags;
}
