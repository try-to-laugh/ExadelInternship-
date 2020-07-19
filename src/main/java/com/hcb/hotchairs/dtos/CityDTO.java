package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CityDTO{
    private Long id;
    private String name;
    private Long countryId;
}



