package com.hcb.hotchairs.controllers;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.services.IPlaceService;
import com.hcb.hotchairs.services.IReservationInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/places")
public class PlaceController {

    private final IPlaceService placeService;
    private final IReservationInfoService reservationInfoService;

    private final Long SINGLE = (long) 1;
    private final Long MEETING = (long) 2;

    @Autowired
    public PlaceController(IPlaceService placeService,
                           IReservationInfoService reservationInfoService) {

        this.placeService = placeService;
        this.reservationInfoService = reservationInfoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @GetMapping("/byFloor/{id}")
    public ResponseEntity<List<PlaceDTO>> getAllByFloorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getAllByFloorId(id));
    }

    @GetMapping("/byOffice/{id}")
    public ResponseEntity<List<PlaceDTO>> getAllByOfficeId(@PathVariable("id") Long officeId){
        return ResponseEntity.ok(placeService.getAllByOfficeId(officeId));
    }

    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }

    @PostMapping("/batch/{floorId}")
    public ResponseEntity<List<PlaceDTO>> savePlaces(@RequestBody List<PlaceDTO> places, @PathVariable Long floorId) {
        return ResponseEntity.ok(placeService.savePlaces(places, floorId));
    }

    /*
        I know this method contradicts all your comments on Bitbucket, but...
     */

    @GetMapping("/batch/{floorId}")
    public ResponseEntity<?> fetchPlaces(@PathVariable(name = "floorId") Long floorId) {
        List<PlaceDTO> places = placeService.getAllByFloorId(floorId);

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ExtendedPlaceInfo {
            @JsonUnwrapped
            PlaceDTO placeDTO;
            Boolean editable;
        }

        return ResponseEntity.ok(places.stream().map((place) ->
                new ExtendedPlaceInfo(place, placeService.isPlaceEditable(place))).collect(Collectors.toList()));
    }
}