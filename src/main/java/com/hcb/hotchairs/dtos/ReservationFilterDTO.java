package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationFilterDTO {
    @NotNull
    private Long currentUserId;
    private Long officeId;
    private Long floorId;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private Time startTime = Time.valueOf("00:00:00");
    private Time endTime = Time.valueOf("23:59:59");
    private Long isMeeting;
    private int[] weekDay;

    List<Long> tagsId = new ArrayList<>();
    List<Long> userIds = new ArrayList<>();
}
