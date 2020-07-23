package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.converters.ReservationConverter;
import com.hcb.hotchairs.converters.UserConverter;
import com.hcb.hotchairs.daos.IReservationDAO;
import com.hcb.hotchairs.daos.IUserDAO;
import com.hcb.hotchairs.dtos.ReservationDTO;
import com.hcb.hotchairs.dtos.UserDTO;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserDAO userDAO;
    private final UserConverter userConverter;
    private final IReservationDAO reservationDAO;
    private final ReservationConverter reservationConverter;

    @Autowired
    public UserService(IUserDAO userDAO, UserConverter userConverter,
                       IReservationDAO reservationDAO, ReservationConverter reservationConverter) {
        this.userDAO = userDAO;
        this.userConverter = userConverter;
        this.reservationDAO = reservationDAO;
        this.reservationConverter = reservationConverter;
    }

    @Override
    public UserDTO getByEmail(String email) {
        return userConverter.toDTO(userDAO.findByEmail(email));
    }

    @Override
    public UserDTO getById(Long id) {
        return userConverter.toDTO(userDAO.findById(id).orElse(null));
    }

    @Override
    public List<UserDTO> getAll() {
        return userDAO.findAll().stream().map(userConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDTO> getUserReservations(Long id) {
        return reservationDAO.findAllByUserId(id).stream().map(reservationConverter::toDTO).collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getNearestUserReservation(Long id) {

        List<ReservationDTO> reservationDTOS =
                reservationDAO.findAllByUserId(id).stream().map(reservationConverter::toDTO).collect(Collectors.toList());

        int count = 0;
        int nearStartDate = 1000000000;
        Date nearStartTime = new Date();
        nearStartTime.setHours(23);
        nearStartTime.setMinutes(59);
        nearStartTime.setSeconds(59);
        Date curDate = new Date();

        for(int i=0; i<reservationDTOS.size(); i++){
            Date resStartDate = reservationDTOS.get(i).getStartDate();
            Date resEndDate = reservationDTOS.get(i).getEndDate();

            if(resEndDate.getDate()-curDate.getDate()>=0) {

                if(curDate.getDate()-resStartDate.getDate()<nearStartDate && curDate.getDate()-resStartDate.getDate()>0){
                    nearStartDate=curDate.getDate()-resStartDate.getDate();

                    count=i;
                }else if((curDate.getDate()-resStartDate.getDate()==nearStartDate)){
                    Date resStartTime=reservationDTOS.get(i).getStartTime();

                    if(nearStartTime.getTime()-resStartTime.getTime()>0){
                        nearStartTime = resStartTime;

                        count=i;
                    }
                }
            }
        }

        return reservationDTOS.get(count);
    }
}
