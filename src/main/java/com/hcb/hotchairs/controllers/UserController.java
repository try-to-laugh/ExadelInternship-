package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.*;
import com.hcb.hotchairs.services.*;
import com.hcb.hotchairs.mas.UserDTOModelAssembler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final UserDTOModelAssembler assembler;
    private final IPlaceService placeService;
    private final IFloorService floorService;
    private final IOfficeService officeService;
    private final ICityService cityService;
    private final ICountryService countryService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ExtendedReservationInfo {
        private ReservationDTO reservation;
        private PlaceDTO place;
        private FloorDTO floor;
        private OfficeDTO office;
        private CityDTO city;
        private CountryDTO country;
    }

    public ExtendedReservationInfo toExtendedReservationInfo(ReservationDTO reservationDTO){

            PlaceDTO placeDTO=placeService.getById(reservationDTO.getPlaceId());
            FloorDTO floorDTO=floorService.getById(placeDTO.getFloorId());
            OfficeDTO officeDTO=officeService.getById(floorDTO.getOfficeId());
            CityDTO cityDTO=cityService.getById(officeDTO.getCityId());
            CountryDTO countryDTO=countryService.getById(cityDTO.getCountryId());

        return new ExtendedReservationInfo(reservationDTO, placeDTO, floorDTO,
                officeDTO, cityDTO, countryDTO);
    }

    @Autowired
    public UserController(IUserService userService, UserDTOModelAssembler assembler,
    IPlaceService placeService, IFloorService floorService, IOfficeService officeService,
                          ICityService cityService, ICountryService countryService) {
        this.userService = userService;
        this.assembler = assembler;
        this.placeService = placeService;
        this.floorService = floorService;
        this.officeService = officeService;
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assembler.toModel(userService.getById(id)));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/current")
    public ResponseEntity<EntityModel<UserDTO>> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(assembler.toModel(userService.getByEmail(authentication.getName())));
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<?> getReservationsByUserId(Long id) {

        List<ExtendedReservationInfo> extendedReservationInfos = new ArrayList<>();

        for(ReservationDTO reservationDTO : userService.getUserReservations(id)) {
            extendedReservationInfos.add(toExtendedReservationInfo(reservationDTO));
        }

        return ResponseEntity.ok(extendedReservationInfos);
    }

    @GetMapping("/reservations/nearest/{id}")
    public ResponseEntity<?> getNearestReservationByUserId(Long id) {
        return ResponseEntity.ok(toExtendedReservationInfo(userService.getNearestUserReservation(id)));
    }

    @GetMapping("/current/reservations")
    public ResponseEntity<?> getCurrentUserReservations(Authentication authentication) {

        List<ExtendedReservationInfo> extendedReservationInfos = new ArrayList<>();

        for(ReservationDTO reservationDTO : userService.getUserReservations(
                userService.getByEmail(authentication.getName()).getId())) {
            extendedReservationInfos.add(toExtendedReservationInfo(reservationDTO));
        }

        return ResponseEntity.ok(extendedReservationInfos);
    }

    @GetMapping("/current/reservations/nearest")
    public ResponseEntity<?> getNearestReservationByUserId(Authentication authentication) {
        return ResponseEntity.ok(toExtendedReservationInfo(userService.getNearestUserReservation(
                userService.getByEmail(authentication.getName()).getId())));
    }
}
