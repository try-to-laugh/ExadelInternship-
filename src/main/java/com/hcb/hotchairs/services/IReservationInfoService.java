package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface IReservationInfoService {
    List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter);

    ReservationInfoDTO saveReservationInfo(ReservationInfoDTO reservationInfo);

    List<ReservationInfoDTO> getIntersectionInfo(ReservationInfoDTO reservationInfo);

    ReservationInfoDTO addToCurrent(ReservationInfoDTO reservationInfo);

    boolean closeByReservationId(Long reservationId);
    boolean closeByHostAndUserId(Long hostId, Long userId);

    @Modifying
    boolean completelyDelete(Long reservationId);
}
