package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FloorDTO {
    private Long id;
    private String name;
    private Long officeId;
}
