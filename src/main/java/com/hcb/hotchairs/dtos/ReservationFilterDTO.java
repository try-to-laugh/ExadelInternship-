package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationFilterDTO {
    private Long currentUserId;

    private Long officeId;
    private Long floorId;

    private Date startDate;
    private Date endDate;

    private Time startTime = Time.valueOf("00:00:00");
    private Time endTime = Time.valueOf("23:59:59");
    private Long isMeeting;
    private int[] weekDay;

    List<Long> tagsId = new ArrayList<>();
    List<Long> usersId = new ArrayList<>();
}
