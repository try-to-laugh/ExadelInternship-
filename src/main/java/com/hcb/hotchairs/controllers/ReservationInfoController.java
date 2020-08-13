package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.services.IReservationInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservationInfo")
public class ReservationInfoController {
    private final IReservationInfoService reservationInfoService;

    public ReservationInfoController(IReservationInfoService reservationInfoService) {
        this.reservationInfoService = reservationInfoService;
    }

    @PostMapping("/free")
    public ResponseEntity<List<ReservationInfoDTO>> getFreePlaces(@RequestBody ReservationFilterDTO request) {
        return ResponseEntity.ok(reservationInfoService.getFreePlace(request));
    }

    @PostMapping("/save")
    public ResponseEntity<ReservationInfoDTO> bookPlace(@RequestBody ReservationInfoDTO request) {
        return ResponseEntity.ok(reservationInfoService.saveReservationInfo(request));
    }

    @PostMapping("/showIntersection")
    public ResponseEntity<List<ReservationInfoDTO>> getIntersection(@RequestBody ReservationInfoDTO request) {
        return ResponseEntity.ok(reservationInfoService.getIntersectionInfo(request));
    }

    @PostMapping("/addToCurrent")
    public ResponseEntity<ReservationInfoDTO> addToCurrent(@RequestBody ReservationInfoDTO request){
        return ResponseEntity.ok(reservationInfoService.addToCurrent(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long reservationId) {
        return (reservationInfoService.deleteReservationById(reservationId)) ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/fromCurrent")
    public ResponseEntity<?> deleteFromCurrent(@RequestParam("hostId") Long hostId,
                                               @RequestParam("userId") Long userId){
        return reservationInfoService.deleteFromExistingByHostAndUser(hostId, userId)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
