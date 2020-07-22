package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private Long hostId;
    private Long userId;
    private Long placeId;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private int[] weekDays;
}
