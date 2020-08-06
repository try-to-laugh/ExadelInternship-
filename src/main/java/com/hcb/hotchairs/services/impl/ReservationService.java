package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.DetailConverter;
import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.daos.IDetailDAO;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.dtos.DetailDTO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.entities.Reservation;
import com.hcb.hotchairs.services.IBotMailSenderService;
import com.hcb.hotchairs.services.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService implements IReservationService {

    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;
    private final IDetailDAO detailDAO;
    private final DetailConverter detailConverter;
    private final IBotMailSenderService mailSender;

    @Autowired
    ReservationService(IReservationDAO reservationDAO,
                       ReservationConverter reservationConverter,
                       IDetailDAO detailDAO,
                       DetailConverter detailConverter,
                       IBotMailSenderService mailSender) {
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
        this.detailDAO = detailDAO;
        this.detailConverter = detailConverter;
        this.mailSender = mailSender;
    }

    @Override
    public List<ReservationDTO> getAll() {
        return reservationDAO.findAll().stream().map(reservationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllByTimeDateAndFloor(Date date, Time startTime, Time endTime, Long floorId) {
        return reservationDAO.findAllByTimeDateAndFloor(date, startTime, endTime, floorId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getAllByTimeDateAndOffice(Date date, Time startTime, Time endTime, Long officeId) {
        return reservationDAO.findAllByTimeDateAndOffice(date, startTime, endTime, officeId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getByTimeDateAndPlace(Date date, Time startTime, Time endTime, Long placeId) {
        return reservationDAO.findByTimeDateAndPlace(date, startTime, endTime, placeId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Modifying
    public ReservationDTO saveReservation(Reservation reservation) {
        ReservationDTO reservationDTO = reservationConverter.toDTO(reservationDAO.save(reservation));
        String sendTo = "someoneuser2020@gmail.com";
        String subject = "hotchairs booking";
        String text = "your booking:" +
                "\nplace: " + reservationDTO.getPlaceId() +
                "\nstart date: " + reservationDTO.getStartDate() +
                "\nend date: " + reservationDTO.getEndDate() +
                "\nstart time: " + reservationDTO.getStartTime() +
                "\nend time: " + reservationDTO.getEndTime();
        mailSender.send(sendTo, subject, text);
        return reservationDTO;
    }

    @Override
    public ReservationDTO getById(Long id) {
        return reservationConverter.toDTO(reservationDAO.findById(id).orElse(null));
    }

    @Override
    public List<DetailDTO> getReservationDetails(Long id) {
        return detailDAO.findByReservationId(id)
                .stream()
                .map(detailConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getIntersectionByDateTimeForUser(Date startDate,
                                                                 Date endDate,
                                                                 Time startTime,
                                                                 Time endTime,
                                                                 Long userId) {

        //List<Reservation> temp =  reservationDAO.findIntersectionForUser(startDate,endDate,startTime,endTime,userId);
        return reservationDAO.findIntersectionForUser(startDate, endDate, startTime, endTime, userId)
                .stream()
                .map(reservationConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Modifying
    @Transactional
    public boolean deleteById(Long reservationId) {
        if (reservationDAO.findById(reservationId).isPresent()) {
            reservationDAO.deleteById(reservationId);
            return true;
        }
        return false;
    }

    @Override
    @Modifying
    public boolean deleteFromCurrentByHostAndUser(Long hostId, Long userId) {
        Optional<Reservation> optionalReservation = reservationDAO.findByHostAnUser(hostId, userId);
        if (optionalReservation.isPresent()) {
            reservationDAO.deleteById(optionalReservation.get().getId());
            return true;
        }
        return false;
    }
}
