package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.*;
import com.hcb.hotchairs.services.IPlaceFilterService;
import com.hcb.hotchairs.services.IPlaceService;
import com.hcb.hotchairs.services.IReservationFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final IPlaceService placeService;
    private final IPlaceFilterService placeFilterService;
    private final IReservationFilterService reservationFilterService;

    private final Long SINGLE = (long) 1;
    private final Long MEETING = (long) 2;

    @Autowired
    public PlaceController(IPlaceService placeService,
                           IPlaceFilterService placeFilterService,
                           IReservationFilterService reservationFilterService) {

        this.placeService = placeService;
        this.placeFilterService = placeFilterService;
        this.reservationFilterService = reservationFilterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @GetMapping("/byFloor/{id}")
    public ResponseEntity<Object> getAllByFloorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getAllByFloorId(id));
    }

    @GetMapping("/byOffice/{id}")
    public ResponseEntity<List<PlaceDTO>> getAllByOfficeId(@PathVariable("id") Long officeId){
        return ResponseEntity.ok(placeService.getAllByOfficeId(officeId));
    }

    @PostMapping("/free")
    public ResponseEntity<List<PlaceDTO>> getFreeMeetingRooms(@RequestBody PlaceFilterDTO request,
                                                              Authentication authentication) {
        return ResponseEntity.ok(placeFilterService.getFreePlaces(request, authentication)
                .stream()
                .filter(place -> request.getIsMeeting().equals(SINGLE)
                        ? place.getCapacity() == 1
                        : place.getCapacity() > 1)
                .collect(Collectors.toList()));
    }

    @PostMapping("/testApi/free")
    public ResponseEntity<List<ReservationInfoDTO>> getFreePlaces(@RequestBody ReservationFilterDTO request,
                                                                  Authentication authentication){
        return ResponseEntity.ok(reservationFilterService.getFreePlace(request,authentication)
                .stream().filter(reservationInfo -> request.getIsMeeting().equals(SINGLE)
                        ? reservationInfo.getCapacity() == 1
                        : reservationInfo.getCapacity() > 1)
                .collect(Collectors.toList()));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }
}