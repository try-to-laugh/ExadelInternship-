package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DateConverter;
import com.hcb.hotchairs.converters.ReservationInfoConverter;
import com.hcb.hotchairs.dtos.PlaceDTO;
import com.hcb.hotchairs.dtos.ReservationFilterDTO;
import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.dtos.TagDTO;
import com.hcb.hotchairs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationFilterService implements IReservationFilterService {

    private final IPlaceService placeService;
    private final ITagService tagService;
    private final DateConverter dateConverter;
    private final IReservationService reservationService;
    private final ReservationInfoConverter reservationInfoConverter;
    private final IFloorService floorService;

    @Autowired
    public ReservationFilterService(IPlaceService placeService,
                                    ITagService tagService,
                                    DateConverter dateConverter,
                                    ReservationInfoConverter reservationInfoConverter,
                                    IReservationService reservationService,
                                    IFloorService floorService) {
        this.placeService = placeService;
        this.tagService = tagService;
        this.dateConverter = dateConverter;
        this.reservationService = reservationService;
        this.reservationInfoConverter = reservationInfoConverter;
        this.floorService = floorService;
    }


    @Override
    public List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter, Authentication authentication) {
        List<Date> requiredDays = (reservationFilter.getStartDate().equals(reservationFilter.getEndDate()))
                ? new ArrayList<>(Arrays.asList(reservationFilter.getStartDate()))
                : dateConverter.toDateList(reservationFilter.getStartDate(),
                reservationFilter.getEndDate(),
                reservationFilter.getWeekDay());

        reservationFilter.setStartTime(Objects.isNull(reservationFilter.getStartTime())
                ? Time.valueOf("00:00:00")
                : reservationFilter.getStartTime());

        reservationFilter.setEndTime(Objects.isNull(reservationFilter.getEndTime())
                ? Time.valueOf("23:59:59")
                : reservationFilter.getEndTime());

        List<TagDTO> requestedTags = (Objects.isNull(reservationFilter.getTagsId()))
                ? new ArrayList<>()
                : tagService.getAllFromIdCollection(reservationFilter.getTagsId());


        List<PlaceDTO> placeAtLocation = (Objects.isNull(reservationFilter.getFloorId()))
                ? placeService.getAllByOfficeId(reservationFilter.getOfficeId())
                : placeService.getAllByFloorId(reservationFilter.getFloorId());

        List<PlaceDTO> placesMatchingTag = placeAtLocation
                .stream()
                .filter(currentPlace -> currentPlace.getTags().containsAll(requestedTags))
                .collect(Collectors.toList());

        for (Date checkedDate : requiredDays) {
            placesMatchingTag = placesMatchingTag
                    .stream()
                    .filter(currentPlace -> Objects.isNull(reservationService.getByTimeDateAndPlace(checkedDate,
                            reservationFilter.getStartTime(),
                            reservationFilter.getEndTime(),
                            currentPlace.getId())))
                    .collect(Collectors.toList());
        }

        return placesMatchingTag
                .stream()
                .map(placeDTO -> reservationInfoConverter.toDTO(placeDTO,
                        floorService.getById(placeDTO.getFloorId()), reservationFilter))
                .collect(Collectors.toList());
    }
}
