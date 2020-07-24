package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.dtos.PlaceFilterDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.RoleDTO;
import com.hcb.hotchairs.dtos.TagDTO;
import com.hcb.hotchairs.services.IPlaceFilterService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlaceFilterService implements IPlaceFilterService {

    private final ReservationService reservationService;
    private final TagService tagService;
    private final PlaceService placeService;
    private final UserService userService;

    public PlaceFilterService(ReservationService reservationService, TagService tagService, PlaceService placeService, UserService userService) {
        this.reservationService = reservationService;
        this.tagService = tagService;
        this.placeService = placeService;
        this.userService = userService;
    }

    @Override
    public List<PlaceDTO> getFreePlaces(PlaceFilterDTO filter, Authentication authentication) {
        List<Long> lockedPlacesId = (Objects.isNull(filter.getFloorId()))
                ? reservationService.getAllByTimeDateAndOffice(
                        filter.getSelectedDate(),
                        Objects.isNull(filter.getStartTime()) ? Time.valueOf("00:00:00") : filter.getStartTime(),
                        Objects.isNull(filter.getEndTime()) ? Time.valueOf("23:59:59") : filter.getEndTime(),
                        filter.getOfficeId())
                    .stream()
                    .map(ReservationDTO::getPlaceId)
                    .collect(Collectors.toList())
                : reservationService.getAllByTimeDateAndFloor(
                        filter.getSelectedDate(),
                        Objects.isNull(filter.getStartTime()) ? Time.valueOf("00:00:00") : filter.getStartTime(),
                        Objects.isNull(filter.getEndTime()) ? Time.valueOf("23:59:59"): filter.getEndTime(),
                        filter.getFloorId())
                    .stream()
                    .map(ReservationDTO::getPlaceId)
                    .collect(Collectors.toList());

        lockedPlacesId.add(Long.valueOf(0));

        List<TagDTO> requestedTags = (Objects.isNull(filter.getTagsId()))
                ? new ArrayList<>()
                : tagService.getAllFromIdCollection(filter.getTagsId());

        List<PlaceDTO> freePlaces = (!Objects.isNull(filter.getFloorId()))
                ? placeService.getFreePlaceOnFloor(lockedPlacesId, filter.getFloorId())
                : placeService.getFreePlaceInOffice(lockedPlacesId, filter.getOfficeId());

        List<PlaceDTO> freePlacesMatchingTags = freePlaces.stream()
                .filter(currentPlace -> currentPlace.getTags().containsAll(requestedTags))
                .collect(Collectors.toList());



//        List<RoleDTO> currentUserRoles = userService.getByEmail(authentication.getName()).getRoles();
//
//        // Not sure we need here .containAll! We discussed that, but maybe it is good architecture decision.
//        List<PlaceDTO> freePlacesMatchingTagsAndRoles = freePlacesMatchingTags
//                .stream()
//                .filter(place -> currentUserRoles.containsAll(place.getRoles()))
//                .collect(Collectors.toList());

        return freePlacesMatchingTags;
    }
}
