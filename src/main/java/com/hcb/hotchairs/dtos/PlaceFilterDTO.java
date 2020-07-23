package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlaceFilterDTO {
    private Long officeId;
    private Long floorId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private Long isMeeting;
    List<Long> tagsId = new ArrayList<>();
}
