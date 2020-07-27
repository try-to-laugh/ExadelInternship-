package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.services.IReservationInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservationInfo")
public class ReservationInfoController {

    private final IReservationInfoService reservationInfoService;
    private static final Long SINGLE = 1L;

    public ReservationInfoController(IReservationInfoService reservationInfoService) {
        this.reservationInfoService = reservationInfoService;
    }

    @PostMapping("/free")
    public ResponseEntity<List<ReservationInfoDTO>> getFreePlaces(@RequestBody ReservationFilterDTO request) {
        return ResponseEntity.ok(reservationInfoService.getFreePlace(request)
                .stream().filter(reservationInfo -> request.getIsMeeting().equals(SINGLE)
                        ? reservationInfo.getCapacity() == 1
                        : reservationInfo.getCapacity() > 1)
                .collect(Collectors.toList()));
    }

    @PostMapping("/save")
    public ResponseEntity<ReservationInfoDTO> bookPlace(@RequestBody ReservationInfoDTO request) {
        return ResponseEntity.ok(reservationInfoService.saveReservationInfo(request));
    }
}
