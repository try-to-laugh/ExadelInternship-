package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IReservationFilterService {
    List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter, Authentication authentication);
}
