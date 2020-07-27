package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ReservationInfoConverter {
    public ReservationInfoDTO toDTO(PlaceDTO placeDTO, FloorDTO floorDTO, ReservationFilterDTO placeFilter){
        if(Objects.isNull(placeDTO)
                || Objects.isNull(floorDTO)
                || Objects.isNull(placeFilter)){
            return null;
        }

        ReservationInfoDTO reservationInfo = new ReservationInfoDTO();
        reservationInfo.setStartDate(placeFilter.getStartDate());
        reservationInfo.setEndDate(placeFilter.getEndDate());
        reservationInfo.setStartTime(placeFilter.getStartTime());
        reservationInfo.setEndTime(placeFilter.getEndTime());
        reservationInfo.setWeekDay(placeFilter.getWeekDay());
        reservationInfo.setUsersId(placeFilter.getUsersId());
        reservationInfo.setCapacity(placeDTO.getCapacity());

        reservationInfo.setPlaceId(placeDTO.getId());
        reservationInfo.setPlaceName(placeDTO.getName());
        reservationInfo.setTags(placeDTO.getTags());

        reservationInfo.setFloorId(floorDTO.getId());
        reservationInfo.setFloorNumber(floorDTO.getNumber());

        return reservationInfo;
    }
}
