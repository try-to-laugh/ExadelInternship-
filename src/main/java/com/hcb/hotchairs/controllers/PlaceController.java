package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.dtos.PlaceRequestDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.TagDTO;
import com.hcb.hotchairs.services.IPlaceService;
import com.hcb.hotchairs.services.IReservationService;
import com.hcb.hotchairs.services.ITagService;
import com.hcb.hotchairs.services.impl.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final IPlaceService placeService;
    private final IReservationService reservationService;
    private final ITagService tagService;

    @Autowired
    public PlaceController(IPlaceService placeService,
                           IReservationService reservationService,
                           ITagService tagService){

        this.placeService = placeService;
        this.reservationService = reservationService;
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(placeService.getById(id));
    }

    @GetMapping("/byFloor/{id}")
    public ResponseEntity<Object> getAllByFloorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(placeService.getAllByFloorId(id));
    }

    @PostMapping("/free")
    public ResponseEntity<Object> test(@RequestBody PlaceRequestDTO request) {

        List<Long> idLockedPlaces = (!Objects.isNull(request.getFloorId())) ?
                reservationService.getAllByDateAndFloor(request.getDate(),request.getFloorId())
                .stream()
                .map(ReservationDTO::getPlaceId)
                .collect(Collectors.toList())
                :
                reservationService.getAllByDateAndOffice(request.getDate(),request.getOfficeId())
                .stream()
                .map(ReservationDTO::getPlaceId)
                .collect(Collectors.toList());


        List<TagDTO> requestedTags = (Objects.isNull(request.getTagsId())) ?
                new ArrayList<>()
                :
                tagService.getAllFromIdCollection(request.getTagsId());

        List<PlaceDTO> freePlaces = (!Objects.isNull(request.getFloorId())) ?
                placeService.getFreePlaceOnFloor(idLockedPlaces,request.getFloorId())
                :
                placeService.getFreePlaceInOffice(idLockedPlaces,request.getOfficeId());

        List<PlaceDTO> freePlaceMatchingTags = freePlaces.stream()
                .filter(currentPlace->currentPlace.getTags().containsAll(requestedTags))
                .collect(Collectors.toList());

        return ResponseEntity.ok(freePlaceMatchingTags);
    }



    @GetMapping("")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(placeService.getAll());
    }
}