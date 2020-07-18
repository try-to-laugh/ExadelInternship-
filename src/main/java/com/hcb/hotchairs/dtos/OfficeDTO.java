package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OfficeDTO {
    private Long id;
    private String street;
    private Long cityId;
}
