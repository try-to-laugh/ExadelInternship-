package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;

import java.util.List;

public interface IReservationInfoService {
    List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter);

    ReservationInfoDTO saveReservationInfo(ReservationInfoDTO reservationInfo);

    List<ReservationInfoDTO> getIntersectionInfo(ReservationInfoDTO reservationInfo);

    ReservationInfoDTO addToCurrent(ReservationInfoDTO reservationInfo);

    boolean deleteReservationById(Long reservationId);
    boolean deleteFromExistingByHostAndUser(Long hostId, Long userId);
}
