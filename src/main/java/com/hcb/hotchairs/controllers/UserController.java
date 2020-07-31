package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.dtos.*;
import com.hcb.hotchairs.mas.UserDTOModelAssembler;
import com.hcb.hotchairs.services.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    private final UserDTOModelAssembler assembler;
    private final IPlaceService placeService;
    private final IFloorService floorService;
    private final IOfficeService officeService;
    private final ICityService cityService;
    private final ICountryService countryService;
    private final IReservationService reservationService;

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
        this.reservationService = reservationService;
    }

    public ExtendedReservationInfo toExtendedReservationInfo(List<DetailDTO> detailDTOs) {


        ReservationDTO reservationDTO = reservationService.getById(detailDTOs.get(0).getReservationId());
        UserDTO userDTO = userService.getById(reservationDTO.getUserId());
        PlaceDTO placeDTO = placeService.getById(reservationDTO.getPlaceId());
        FloorDTO floorDTO = floorService.getById(placeDTO.getFloorId());
        OfficeDTO officeDTO = officeService.getById(floorDTO.getOfficeId());
        CityDTO cityDTO = cityService.getById(officeDTO.getCityId());
        CountryDTO countryDTO = countryService.getById(cityDTO.getCountryId());

        return new ExtendedReservationInfo(userDTO, reservationDTO, detailDTOs.get(0), placeDTO, floorDTO,
                officeDTO, cityDTO, countryDTO);
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
        if (Objects.isNull(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(assembler.toModel(userService.getByEmail(authentication.getName())));
        }
    }

    @GetMapping("/reservations/{id}")
    public ResponseEntity<List<Object>> getReservationsByUserId(@PathVariable(name = "id") Long id) {

        List<ReservationDTO> userReservations = userService.getUserReservations(id);
        boolean userHasReservations = !CollectionUtils.isEmpty(userReservations);

        if (!userHasReservations) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(userReservations.stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/reservations/nearest/{id}")
    public ResponseEntity<?> getNearestReservationByUserId(@PathVariable(name = "id") Long id) {

        List<DetailDTO> userDetails = userService.getUserDetails(id);
        boolean userHasDetails = !CollectionUtils.isEmpty(userDetails);

        if (!userHasDetails) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(toExtendedReservationInfo(userDetails));
    }

    @GetMapping("/current/reservations")
    public ResponseEntity<List<Object>> getCurrentUserReservations
            (Authentication authentication) {

        if (Objects.isNull(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = userService.getByEmail(authentication.getName());
        List<ReservationDTO> userReservations = userService.getUserReservations(user.getId());
        boolean userHasReservations = !CollectionUtils.isEmpty(userReservations);

        if (!userHasReservations) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(userReservations.stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/current/reservations/nearest")
    public ResponseEntity<?> getCurrentUserNearestReservation
            (Authentication authentication) {

        if (Objects.isNull(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO user = userService.getByEmail(authentication.getName());
        List<DetailDTO> userDetails = userService.getUserDetails(user.getId());
        boolean userHasDetails = !CollectionUtils.isEmpty(userDetails);

        if (!userHasDetails) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(toExtendedReservationInfo(userDetails));
    }

    @GetMapping("/subordinate/{id}")
    public ResponseEntity<List<UserDTO>> getSubordinate(@PathVariable("id") Long hrId) {
        return ResponseEntity.ok(userService.getByHrId(hrId));
    }

    @GetMapping("/current/subordinate")
    public ResponseEntity<List<UserDTO>> getCurrentUserSubordinate(Authentication authentication) {
        UserDTO currentUser = userService.getByEmail(authentication.getName());
        return ResponseEntity.ok(userService.getByHrId(currentUser.getId()));
    }

    @GetMapping("extended/paging")
    public ResponseEntity<?> getPagedAndSortedUsers(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                    @RequestParam(name = "pageSize") Integer pageSize,
                                                    @RequestParam(name = "sortMethod", defaultValue = "id") String sortMethod,
                                                    @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection) {
        List<UserDTO> users = userService.getPagedAndSorted(pageNumber, pageSize, sortMethod, sortDirection);

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        class ExtendedUserInfo {
            Long id;
            String name;
            String email;
            List<RoleDTO> roles;
            UserDTO hr;
        }

        return ResponseEntity.ok(users
                .stream()
                .map(user -> new ExtendedUserInfo(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles(),
                        Objects.isNull(user.getHrId()) ? null : userService.getById(user.getHrId())))
                .collect(Collectors.toList()));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUsersCount() {
        return ResponseEntity.ok(userService.getUsersCount());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<List<RoleDTO>> getUserRoles(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id).getRoles());
    }

    @PostMapping("/roles/{id}")
    public ResponseEntity<List<RoleDTO>> setUserRoles(@RequestBody List<RoleDTO> roles, @PathVariable Long id) {
        return ResponseEntity.ok(userService.setUserRoles(roles, id));
    }

    @PostMapping("/reservations/staff/{id}")
    public ResponseEntity<List<ExtendedReservationInfo>> getStaffReservationsByUserId
            (@PathVariable Long id, @RequestBody List<String> filtersInfo) {

        UserDTO user = userService.getById(id);
        List<ReservationDTO> staffReservations = userService.getStaffReservations(user.getId());
        boolean staffHasReservations = !CollectionUtils.isEmpty(staffReservations);

        if (!staffHasReservations) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<ExtendedReservationInfo> staffExtendedReservations = staffReservations.stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList());
        List<ExtendedReservationInfo> staffFilterExtendedReservations;

        if (!StringUtils.isEmpty(filtersInfo.get(0))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.user.getName().equals(filtersInfo.get(0)))
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }
        staffExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(1))) {
            staffExtendedReservations = staffFilterExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.country.getName().equals(filtersInfo.get(1)))
                    .collect(Collectors.toList());
        } else {
            staffExtendedReservations = staffFilterExtendedReservations.stream().collect(Collectors.toList());
        }
        staffFilterExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(2))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.city.getName().equals(filtersInfo.get(2)))
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }
        staffExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(3))) {
            staffExtendedReservations = staffFilterExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.office.getStreet().equals(filtersInfo.get(3)))
                    .collect(Collectors.toList());
        } else {
            staffExtendedReservations = staffFilterExtendedReservations.stream().collect(Collectors.toList());
        }
        staffFilterExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(4))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(reservation -> {

                        List<DetailDTO> reservationDetails =
                                reservationService.getReservationDetails(reservation.reservation.getId());

                        DetailDTO hasData = reservationDetails.stream()
                                .filter(detail -> detail.getDate().compareTo(java.sql.Date.valueOf(filtersInfo.get(4))) == 0)
                                .findFirst().orElse(null);

                        if (hasData == null)
                            return false;

                        reservation.detail.setDate(hasData.getDate());

                        return true;
                    })
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }

        return ResponseEntity.ok(staffFilterExtendedReservations);
    }

    @PostMapping("current/reservations/staff")
    public ResponseEntity<List<ExtendedReservationInfo>> getCurrentUserStaffReservations
            (Authentication authentication, @RequestBody List<String> filtersInfo) {

        if (Objects.isNull(authentication)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO currentUser = userService.getByEmail(authentication.getName());
        List<ReservationDTO> staffReservations = userService.getStaffReservations(currentUser.getId());
        boolean staffHasReservations = !CollectionUtils.isEmpty(staffReservations);

        if (!staffHasReservations) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        List<ExtendedReservationInfo> staffExtendedReservations = staffReservations.stream()
                .map(res -> toExtendedReservationInfo(reservationService.getReservationDetails(res.getId())))
                .collect(Collectors.toList());
        List<ExtendedReservationInfo> staffFilterExtendedReservations;

        if (!StringUtils.isEmpty(filtersInfo.get(0))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.user.getName().equals(filtersInfo.get(0)))
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }
        staffExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(1))) {
            staffExtendedReservations = staffFilterExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.country.getName().equals(filtersInfo.get(1)))
                    .collect(Collectors.toList());
        } else {
            staffExtendedReservations = staffFilterExtendedReservations.stream().collect(Collectors.toList());
        }
        staffFilterExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(2))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.city.getName().equals(filtersInfo.get(2)))
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }
        staffExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(3))) {
            staffExtendedReservations = staffFilterExtendedReservations.stream()
                    .filter(staffReservation -> staffReservation.office.getStreet().equals(filtersInfo.get(3)))
                    .collect(Collectors.toList());
        } else {
            staffExtendedReservations = staffFilterExtendedReservations.stream().collect(Collectors.toList());
        }
        staffFilterExtendedReservations.clear();

        if (!StringUtils.isEmpty(filtersInfo.get(4))) {
            staffFilterExtendedReservations = staffExtendedReservations.stream()
                    .filter(reservation -> {

                        List<DetailDTO> reservationDetails =
                                reservationService.getReservationDetails(reservation.reservation.getId());

                        DetailDTO hasData = reservationDetails.stream()
                                .filter(detail -> detail.getDate().compareTo(java.sql.Date.valueOf(filtersInfo.get(4))) == 0)
                                .findFirst().orElse(null);

                        if (hasData == null)
                            return false;

                        reservation.detail.setDate(hasData.getDate());

                        return true;
                    })
                    .collect(Collectors.toList());
        } else {
            staffFilterExtendedReservations = staffExtendedReservations.stream().collect(Collectors.toList());
        }

        return ResponseEntity.ok(staffFilterExtendedReservations);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class ExtendedReservationInfo {
        private UserDTO user;
        private ReservationDTO reservation;
        private DetailDTO detail;
        private PlaceDTO place;
        private FloorDTO floor;
        private OfficeDTO office;
        private CityDTO city;
        private CountryDTO country;
    }

    /* TODO:
        1) Add some checks with exceptions
     */
}