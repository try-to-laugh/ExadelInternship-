package com.hcb.hotchairs.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReservationInfoDTO {
    @NotNull
    private Long currentUserId;
    private Long reservationId;
    private Long hostId;
    private Long placeId;
    private String placeName;
    private Long capacity;
    private List<TagDTO> tags;
    private Long floorId;
    private Integer floorNumber;
    @NotNull
    private Time startTime;
    @NotNull
    private Time endTime;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private int[] weekDay;
    private List<Long> usersId = new ArrayList<>();

}
