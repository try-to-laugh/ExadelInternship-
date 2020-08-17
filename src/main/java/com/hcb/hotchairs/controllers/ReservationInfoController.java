package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.services.IBotMailSenderService;
import com.hcb.hotchairs.services.IReservationInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservationInfo")
public class ReservationInfoController {
    private final IReservationInfoService reservationInfoService;
    private final IBotMailSenderService mailSenderService;

    public ReservationInfoController(IReservationInfoService reservationInfoService,
                                     IBotMailSenderService mailSenderService) {
        this.reservationInfoService = reservationInfoService;
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/free")
    public ResponseEntity<List<ReservationInfoDTO>> getFreePlaces(@RequestBody ReservationFilterDTO request) {
        return ResponseEntity.ok(reservationInfoService.getFreePlace(request));
    }

    @PostMapping("/save")
    public ResponseEntity<ReservationInfoDTO> bookPlace(@RequestBody ReservationInfoDTO request) {
        ReservationInfoDTO result = reservationInfoService.saveReservationInfo(request);
        try {
            mailSenderService.send(result);
        } catch (Exception e) {
            // I didn't want to create logger now=) And if something bad happen there, i don't want FRONT knew that)
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/showIntersection")
    public ResponseEntity<List<ReservationInfoDTO>> getIntersection(@RequestBody ReservationInfoDTO request) {
        return ResponseEntity.ok(reservationInfoService.getIntersectionInfo(request));
    }

    @PostMapping("/addToCurrent")
    public ResponseEntity<ReservationInfoDTO> addToCurrent(@RequestBody ReservationInfoDTO request) {
        ReservationInfoDTO result = reservationInfoService.addToCurrent(request);
        try {
            mailSenderService.send(result);
        } catch (Exception e) {
            // I didn't want to create logger now=) And if something bad happen there, i don't want FRONT knew that)
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable("id") Long reservationId) {
        return (reservationInfoService.closeByReservationId(reservationId))
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/fromCurrent")
    public ResponseEntity<?> deleteFromCurrent(@RequestParam("hostId") Long hostId,
                                               @RequestParam("userId") Long userId) {
        return reservationInfoService.closeByHostAndUserId(hostId, userId)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/completelyRemove/{id}")
    public ResponseEntity<?> completelyRemove(@PathVariable("id") Long id) {
        return reservationInfoService.completelyDelete(id)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
