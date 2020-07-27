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

import java.util.List;
import java.util.stream.Collectors;

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
    private final IReservationService reservationService;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ExtendedReservationInfo {
        private ReservationDTO reservation;
        private List<DetailDTO> details;
        private PlaceDTO place;
        private FloorDTO floor;
        private OfficeDTO office;
        private CityDTO city;
        private CountryDTO country;
    }

    public ExtendedReservationInfo toExtendedReservationInfo(List<DetailDTO> detailDTOs) {

        ReservationDTO reservationDTO = reservationService.getById(detailDTOs.get(0).getReservationId());
        PlaceDTO placeDTO = placeService.getById(reservationDTO.getPlaceId());
        FloorDTO floorDTO = floorService.getById(placeDTO.getFloorId());
        OfficeDTO officeDTO = officeService.getById(floorDTO.getOfficeId());
        CityDTO cityDTO = cityService.getById(officeDTO.getCityId());
        CountryDTO countryDTO = countryService.getById(cityDTO.getCountryId());

        return new ExtendedReservationInfo(reservationDTO, detailDTOs, placeDTO, floorDTO,
                officeDTO, cityDTO, countryDTO);
    }

    @Autowired
    public UserController(IUserService userService, UserDTOModelAssembler assembler,
    IPlaceService placeService, IFloorService floorService, IOfficeService officeService,
            ICityService cityService, ICountryService countryService, IReservationService reservationService) {
        this.userService = userService;
        this.assembler = assembler;
        this.placeService = placeService;
        this.floorService = floorService;
        this.officeService = officeService;
        this.cityService = cityService;
        this.countryService = countryService;
        this.reservationService=reservationService;
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
        return ResponseEntity.ok(userService.getUserReservations(id).stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/reservations/nearest/{id}")
    public ResponseEntity<?> getNearestReservationByUserId(Long id) {
        return ResponseEntity.ok(
                toExtendedReservationInfo(
                        reservationService.getReservationDetails(
                                userService.getUserReservations(id).get(0).getId())));
    }

    @GetMapping("/current/reservations")
    public ResponseEntity<?> getCurrentUserReservations(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserReservations
                (userService.getByEmail(authentication.getName()).getId())
                .stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/current/reservations/nearest")
   public ResponseEntity<?> getNearestReservationByUserId(Authentication authentication) {
        return ResponseEntity.ok(
                toExtendedReservationInfo(
                        reservationService.getReservationDetails(
                                userService.getUserReservations(
                                        userService.getByEmail(authentication.getName()).getId()).get(0).getId())));
    }
}
