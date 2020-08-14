package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.services.IBotMailSenderService;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class BotMailSenderService implements IBotMailSenderService {
    private final IUserService userService;
    private final JavaMailSender mailSender;
    private static final String[] dayOfWeek = {
            "monday\n",
            "tuesday\n",
            "wednesday\n",
            "thursday\n",
            "friday\n",
            "saturday\n",
            "sunday\n"
    };

    @Value("${spring.mail.username}")
    private String serviceUsername;

    @Autowired
    public BotMailSenderService(JavaMailSender mailSender,
                                IUserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    @Override
    public void send(String sendTo, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(serviceUsername);
        mailMessage.setTo(sendTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        mailSender.send(mailMessage);
    }

    @Override
    public void send(ReservationInfoDTO reservationInfo) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        String subject = this.createSubject(reservationInfo);
        String[] text = this.createText(reservationInfo);


        mailMessage.setFrom(serviceUsername);
        mailMessage.setTo(userService.getById(reservationInfo.getCurrentUserId()).getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(text[0]);


        mailSender.send(mailMessage);
        if (reservationInfo.getUsersId() != null &&
                !reservationInfo.getUsersId().isEmpty()) {
            for (Long userId : reservationInfo.getUsersId()) {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(userService.getById(userId).getEmail());
                mail.setFrom(serviceUsername);
                mail.setSubject(subject);
                mail.setText(text[1]);
                mailSender.send(mail);
            }
        }

    }

    private String createSubject(ReservationInfoDTO reservationInfo) {
        return "Booking at hotchairs.com";
    }

    private String[] createText(ReservationInfoDTO reservationInfo) {
        StringBuilder infoPart = new StringBuilder();
        infoPart.append("Place Name: ")
                .append(reservationInfo.getPlaceName())
                .append("\nFloor: ")
                .append(reservationInfo.getFloorNumber());

        if ((reservationInfo.getStartDate().equals(reservationInfo.getEndDate()))) {
            infoPart.append("\nDate: ")
                    .append(reservationInfo.getStartDate());
        } else {
            infoPart.append("\nDate: ")
                    .append(reservationInfo.getStartDate())
                    .append("-")
                    .append(reservationInfo.getEndDate());
        }

        if ((reservationInfo.getStartTime().toLocalTime().equals(LocalTime.of(0, 0, 0))
                || reservationInfo.getEndTime().toLocalTime().equals(LocalTime.of(23, 59, 59)))) {
            infoPart.append("\nTime: Full Day");
        } else {
            infoPart.append("\nTime: ")
                    .append(reservationInfo.getStartTime())
                    .append("-")
                    .append(reservationInfo.getEndTime().toLocalTime());
        }


        if (reservationInfo.getWeekDay() != null && reservationInfo.getWeekDay().length != 0) {
            infoPart.append("\nDays:\n");
            for (int item : reservationInfo.getWeekDay()) {
                infoPart.append(dayOfWeek[item]);
            }
        }


        StringBuilder mainPersonMessage = new StringBuilder();
        if (reservationInfo.getHostId() == null
                || reservationInfo.getHostId().equals(reservationInfo.getReservationId())) {
            mainPersonMessage.append("You book place\n");
        } else {
            if ((reservationInfo.getCapacity().equals(1L))) {
                mainPersonMessage.append("Place booked for you\n");
            } else {
                mainPersonMessage.append("You are invited to the meeting\n");
            }
        }
        mainPersonMessage.append(infoPart);

        return new String[]{
                mainPersonMessage.toString(),
                "You invited for meeting\n" + infoPart
        };
    }
}
