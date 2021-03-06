package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DateConverter;
import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.converters.ReservationInfoConverter;
import com.hcb.hotchairs.dtos.*;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.exceptions.CollisionException;
import com.hcb.hotchairs.exceptions.DateMissingException;
import com.hcb.hotchairs.exceptions.RowDoesNotExistException;
import com.hcb.hotchairs.exceptions.WrongSeatTypeException;
import com.hcb.hotchairs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
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

    private static final Long SINGLE = 1L;

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
    @Transactional
    public List<ReservationInfoDTO> getFreePlace(ReservationFilterDTO reservationFilter) {
        if (!Objects.isNull(reservationFilter.getUsersId())
                && !reservationFilter.getUsersId().isEmpty()
                && reservationFilter.getIsMeeting().equals(SINGLE)) {
            throw new WrongSeatTypeException();
        }

        List<Date> requiredDays = dateConverter.toDateList(reservationFilter.getStartDate(),
                reservationFilter.getEndDate(),
                reservationFilter.getWeekDay());
        if (requiredDays.isEmpty()) {
            throw new DateMissingException();
        }

        List<TagDTO> requestedTags = (Objects.isNull(reservationFilter.getTagsId()))
                ? Collections.emptyList()
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
                    .filter(currentPlace -> reservationService.getByTimeDateAndPlace(checkedDate,
                            reservationFilter.getStartTime(),
                            reservationFilter.getEndTime(),
                            currentPlace.getId()).isEmpty())
                    .collect(Collectors.toList());
        }

        return placesMatchingTag
                .stream()
                .filter(place -> (reservationFilter.getIsMeeting().equals(SINGLE))
                        ? place.getCapacity().equals(SINGLE)
                        : place.getCapacity() > 1)
                .map(placeDTO -> reservationInfoConverter.toDTO(placeDTO,
                        floorService.getById(placeDTO.getFloorId()), reservationFilter))
                .collect(Collectors.toList());
    }

    @Transactional
    @Modifying
    @Override
    public ReservationInfoDTO saveReservationInfo(ReservationInfoDTO reservationInfo) {
        if (!Objects.isNull(reservationInfo.getUsersId())
                && !reservationInfo.getUsersId().isEmpty()
                && reservationInfo.getCapacity().equals(SINGLE)) {
            throw new WrongSeatTypeException();
        }

        if (!isFree(reservationInfo)) {
            throw new CollisionException();
        }

        List<Date> requiredDate = dateConverter.toDateList(reservationInfo.getStartDate(),
                reservationInfo.getEndDate(),
                reservationInfo.getWeekDay());
        if (requiredDate.isEmpty()) {
            throw new DateMissingException();
        }

        Reservation hostReservation = reservationConverter.fromDTO(reservationInfo, null);
        ReservationDTO hostReservationDTO = reservationService.saveReservation(hostReservation);

        requiredDate.forEach(currentDay ->
                detailService.saveDetail(detailConverter.fromDTO(currentDay, hostReservationDTO.getId())));

        if (!Objects.isNull(reservationInfo.getUsersId())) {
            Long currentHostId = reservationInfo.getCurrentUserId();

            List<Long> users = reservationInfo.getUsersId();
            users = users.stream()
                    .distinct()
                    .filter(id -> !id.equals(reservationInfo.getCurrentUserId()))
                    .collect(Collectors.toList());

            for (Long userId : users) {
                reservationInfo.setCurrentUserId(userId);
                ReservationDTO currentReservationDTO = reservationService
                        .saveReservation(reservationConverter.fromDTO(reservationInfo, hostReservationDTO.getId()));

                requiredDate.forEach(currentDay ->
                        detailService.saveDetail(detailConverter.fromDTO(currentDay, currentReservationDTO.getId())));
            }
            reservationInfo.setCurrentUserId(currentHostId);
        }

        return reservationInfoConverter.toDTO(
                placeService.getById(hostReservation.getPlace().getId()),
                floorService.getById(placeService.getById(hostReservation.getPlace().getId()).getFloorId()),
                hostReservationDTO,
                reservationInfo.getUsersId());
    }

    @Override
    public List<ReservationInfoDTO> getIntersectionInfo(ReservationInfoDTO reservationInfo) {
        List<Date> requiredDate = dateConverter.toDateList(reservationInfo.getStartDate(),
                reservationInfo.getEndDate(), reservationInfo.getWeekDay());
        if (requiredDate.isEmpty()) {
            throw new DateMissingException();
        }

        List<ReservationDTO> probablyIntersectionReservations = reservationService.getIntersectionByDateTimeForUser(
                reservationInfo.getStartDate(),
                reservationInfo.getEndDate(),
                reservationInfo.getStartTime(),
                reservationInfo.getEndTime(),
                reservationInfo.getCurrentUserId()
        );

        List<ReservationDTO> intersectionReservation = new ArrayList<>();
        for (ReservationDTO reservation : probablyIntersectionReservations) {

            List<LocalDate> dates = requiredDate.stream().map(Date::toLocalDate).collect(Collectors.toList());
            dates.retainAll(new HashSet<>(detailService
                    .getActiveByReservationId(reservation.getId())
                    .stream()
                    .map(DetailDTO::getDate)
                    .map(Date::toLocalDate)
                    .collect(Collectors.toList())));

            if (!dates.isEmpty()) {
                intersectionReservation.add(reservation);
            }
        }

        return intersectionReservation
                .stream()
                .map(currentRes -> reservationInfoConverter.toDTO(
                        placeService.getById(currentRes.getPlaceId()),
                        floorService.getById(placeService.getById(currentRes.getPlaceId()).getFloorId()),
                        currentRes,
                        Collections.emptyList()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @Modifying
    public ReservationInfoDTO addToCurrent(ReservationInfoDTO reservationInfo) {
        if (Objects.isNull(reservationInfo.getHostId())) {
            throw new RowDoesNotExistException();
        }

        ReservationDTO currentReservation = reservationService.getById(reservationInfo.getHostId());
        if (Objects.isNull(currentReservation)) {
            throw new RowDoesNotExistException();
        } else if (placeService.getById(currentReservation.getPlaceId()).getCapacity().equals(SINGLE)) {
            throw new WrongSeatTypeException();
        }

        List<Date> dates = dateConverter.toDateList(currentReservation.getStartDate(),
                currentReservation.getEndDate(),
                currentReservation.getWeekDays());

        currentReservation.setHostId(currentReservation.getId());
        currentReservation.setId(0L);
        currentReservation.setUserId(reservationInfo.getCurrentUserId());

        ReservationDTO reservationForCurrentUser = reservationService.saveReservation(
                reservationConverter.fromDTO(currentReservation));
        for (Date currentDate : dates) {
            detailService.saveDetail(detailConverter.fromDTO(currentDate, reservationForCurrentUser.getId()));
        }

        return reservationInfoConverter.toDTO(placeService.getById(reservationForCurrentUser.getPlaceId()),
                floorService.getById(placeService.getById(reservationForCurrentUser.getPlaceId()).getFloorId()),
                reservationForCurrentUser,
                null);
    }

    @Override
    @Modifying
    @Transactional
    public boolean closeByReservationId(Long reservationId) {
        ReservationDTO reservationToDelete = reservationService.getById(reservationId);
        if (reservationToDelete == null) {
            return false;
        }

        Date newEndDate = Date.valueOf(LocalDate.now().minusDays(1));
        if (reservationToDelete.getEndDate().compareTo(newEndDate) <= 0) {
            return true;
        }

        List<ReservationDTO> dependentReservation = reservationService.getAllByHostId(reservationId);
        ReservationDTO deletedReservation = reservationService.deleteById(reservationId);
        if (deletedReservation.getStartDate().compareTo(newEndDate) > 0) {
            return true;
        }
        List<Date> newDays = dateConverter.toDateList(deletedReservation.getStartDate(),
                newEndDate,
                deletedReservation.getWeekDays());
        if (newDays.isEmpty()) {
            return true;
        }

        deletedReservation.setId(null);
        deletedReservation.setHostId(null);
        deletedReservation.setEndDate(newEndDate);
        ReservationDTO newHostReservation = reservationService
                .saveReservation(reservationConverter.fromDTO(deletedReservation));
        for (Date day : newDays) {
            detailService.saveDetail(detailConverter.fromDTO(day, newHostReservation.getId()));
        }

        for (ReservationDTO reservation : dependentReservation) {
            reservation.setId(null);
            reservation.setHostId(newHostReservation.getId());
            reservation.setEndDate(newEndDate);
            ReservationDTO newReservation = reservationService
                    .saveReservation(reservationConverter.fromDTO(reservation));
            for (Date day : newDays) {
                detailService.saveDetail(detailConverter.fromDTO(day, newReservation.getId()));
            }
        }
        return true;
    }

    @Override
    @Modifying
    public boolean closeByHostAndUserId(Long hostId, Long userId) {
        ReservationDTO toDelete = reservationService.getByHostAndUser(hostId, userId);
        if (toDelete != null) {
            this.closeByReservationId(toDelete.getId());
            return true;
        }
        return false;
    }

    public boolean isFree(ReservationInfoDTO reservationInfo) {
        List<Date> dates = dateConverter.toDateList(reservationInfo.getStartDate(),
                reservationInfo.getEndDate(),
                reservationInfo.getWeekDay());
        if (dates.isEmpty()) {
            throw new DateMissingException();
        }

        List<ReservationDTO> probablyIntersect = reservationService
                .getAllByPlaceInDateTimeRange(reservationInfo.getPlaceId(),
                        reservationInfo.getStartDate(),
                        reservationInfo.getEndDate(),
                        reservationInfo.getStartTime(),
                        reservationInfo.getEndTime());

        for (ReservationDTO res : probablyIntersect) {
            List<LocalDate> newResDays = dates.stream().map(Date::toLocalDate).collect(Collectors.toList());
            newResDays.retainAll(new HashSet<>(detailService
                    .getActiveByReservationId(res.getId()))
                    .stream()
                    .map(DetailDTO::getDate)
                    .map(Date::toLocalDate)
                    .collect(Collectors.toList()));

            if (!newResDays.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Modifying
    @Override
    public boolean completelyDelete(Long reservationId) {
        return reservationService.deleteById(reservationId) != null;
    }
}
