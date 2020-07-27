package com.hcb.hotchairs.converters;

import com.hcb.hotchairs.dtos.*;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class ReservationInfoConverter {
    public ReservationInfoDTO toDTO(PlaceDTO placeDTO, FloorDTO floorDTO, ReservationFilterDTO placeFilter){
        if(Objects.isNull(placeDTO) || Objects.isNull(floorDTO) || Objects.isNull(placeFilter)){
            return null;
        }

        ReservationInfoDTO reservationInfo = new ReservationInfoDTO();
        reservationInfo.setStartDate(placeFilter.getStartDate());
        reservationInfo.setEndDate(placeFilter.getEndDate());
        reservationInfo.setStartTime(placeFilter.getStartTime());
        reservationInfo.setEndTime(placeFilter.getEndTime());
        reservationInfo.setWeekDay(placeFilter.getWeekDay());
        reservationInfo.setUsersId(placeFilter.getUserIds());
        reservationInfo.setCapacity(placeDTO.getCapacity());

        reservationInfo.setPlaceId(placeDTO.getId());
        reservationInfo.setPlaceName(placeDTO.getName());
        reservationInfo.setTags(placeDTO.getTags());

        reservationInfo.setFloorId(floorDTO.getId());
        reservationInfo.setFloorNumber(floorDTO.getNumber());

        reservationInfo.setCurrentUserId(placeFilter.getCurrentUserId());
        return reservationInfo;
    }

    public ReservationInfoDTO toDTO(PlaceDTO placeDTO, FloorDTO floorDTO, ReservationDTO reservationDTO){
        if(Objects.isNull(placeDTO) || Objects.isNull(floorDTO) || Objects.isNull(reservationDTO)){
            return null;
        }

        ReservationInfoDTO reservationInfoDTO = new ReservationInfoDTO();

        reservationInfoDTO.setCapacity(placeDTO.getCapacity());
        reservationInfoDTO.setPlaceId(placeDTO.getId());
        reservationInfoDTO.setPlaceName(placeDTO.getName());

        reservationInfoDTO.setFloorId(floorDTO.getId());
        reservationInfoDTO.setFloorNumber(floorDTO.getNumber());

        reservationInfoDTO.setStartDate(reservationDTO.getStartDate());
        reservationInfoDTO.setEndDate(reservationDTO.getEndDate());
        reservationInfoDTO.setStartTime(reservationDTO.getStartTime());
        reservationInfoDTO.setEndTime(reservationDTO.getEndTime());
        reservationInfoDTO.setHostId(reservationDTO.getHostId());
        reservationInfoDTO.setWeekDay(reservationDTO.getWeekDays());
        reservationInfoDTO.setReservationId(reservationDTO.getId());
        reservationInfoDTO.setCurrentUserId(reservationDTO.getUserId());

        return reservationInfoDTO;
    }
}
