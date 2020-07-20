package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlaceDTO {
    private Long id;
    private Long capacity;
    private Long florId;
    private String name;
}
