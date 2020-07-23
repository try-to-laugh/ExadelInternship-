package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlaceRequestDTO {
    private Long officeId;
    private Long floorId;
    private Date date;
    private Long seatType;
    List<Long> tagsId = new ArrayList<>();
}
