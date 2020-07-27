package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DateConverter;
import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.converters.ReservationInfoConverter;
import com.hcb.hotchairs.dtos.*;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReservationInfoService implements IReservationInfoService {

    private final IPlaceService placeService;
    private final ITagService tagService;
    private final DateConverter dateConverter;
    private final IReservationService reservationService;
    private final ReservationInfoConverter reservationInfoConverter;
    private final IFloorService floorService;
    private final ReservationConverter reservationConverter;
    private final IDetailService detailService;
    private final DetailConverter detailConverter;

    @Autowired
    public ReservationInfoService(IPlaceService placeService,
                                  ITagService tagService,
                                  DateConverter dateConverter,
                                  ReservationInfoConverter reservationInfoConverter,
                                  IReservationService reservationService,
                                  IFloorService floorService,
                                  ReservationConverter reservationConverter,
                                  IDetailService detailService,
                                  DetailConverter detailConverter) {
        this.placeService = placeService;
        this.tagService = tagService;
        this.dateConverter = dateConverter;
        this.reservationService = reservationService;
        this.reservationInfoConverter = reservationInfoConverter;
        this.floorService = floorService;
        this.reservationConverter = reservationConverter;
        this.detailService = detailService;
        this.detailConverter = detailConverter;
    }


    @Override
    public List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter) {
        List<Date> requiredDays = dateConverter.toDateList(reservationFilter.getStartDate(),
                reservationFilter.getEndDate(), reservationFilter.getWeekDay());

        System.out.println(reservationFilter.getEndTime() + "     " + reservationFilter.getStartTime());

        List<TagDTO> requestedTags = (Objects.isNull(reservationFilter.getTagsId())) ? new ArrayList<>()
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

    @Transactional
    @Override
    public ReservationInfoDTO saveReservationInfo(ReservationInfoDTO reservationInfo) {

        Reservation hostReservation = reservationConverter.fromDTO(reservationInfo, null);
        ReservationDTO hostReservationDTO = reservationService.saveReservation(hostReservation);

        List<Date> requiredDate = dateConverter.toDateList(reservationInfo.getStartDate(),
                reservationInfo.getEndDate(), reservationInfo.getWeekDay());
        requiredDate.forEach(currentDay ->
                detailService.saveDetail(detailConverter.fromDTO(currentDay, hostReservationDTO.getId())));

        if (!Objects.isNull(reservationInfo.getUsersId())) {
            for (Long userId : reservationInfo.getUsersId()) {
                ReservationDTO currentReservationDTO = reservationService
                        .saveReservation(reservationConverter.fromDTO(reservationInfo, hostReservationDTO.getId()));

                requiredDate.forEach(currentDay ->
                        detailService.saveDetail(detailConverter.fromDTO(currentDay, currentReservationDTO.getId())));
            }
        }

        return reservationInfoConverter.toDTO(
                placeService.getById(hostReservation.getPlace().getId()),
                floorService.getById(placeService.getById(hostReservation.getPlace().getId()).getFloorId()),
                hostReservationDTO);
    }
}
