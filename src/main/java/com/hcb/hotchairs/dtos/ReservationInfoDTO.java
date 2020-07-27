package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReservationInfoDTO {
    private Long currentUserId;

    private Long reservationId;
    private Long hostId;

    private Long placeId;
    private String placeName;
    private Long capacity;
    private List<TagDTO> tags;

    private Long floorId;
    private Integer floorNumber;

    private Time startTime;
    private Time endTime;
    private Date startDate;
    private Date endDate;
    private int[] weekDay;

    private List<Long> usersId = new ArrayList<>();
}
