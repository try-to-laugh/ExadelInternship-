package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.entities.Reservation;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IReservationInfoService {
    List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter);

    ReservationInfoDTO saveReservationInfo(ReservationInfoDTO reservationInfo);
}
