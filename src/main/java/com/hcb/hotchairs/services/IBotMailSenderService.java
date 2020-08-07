package com.hcb.hotchairs.services;

import com.hcb.hotchairs.dtos.ReservationInfoDTO;

public interface IBotMailSenderService {
    void send(String sendTo,String subject, String text);
    void send(ReservationInfoDTO reservationInfo);
}
