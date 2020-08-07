package com.hcb.hotchairs.services.impl;

import com.hcb.hotchairs.dtos.ReservationInfoDTO;
import com.hcb.hotchairs.services.IBotMailSenderService;
import com.hcb.hotchairs.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class BotMailSenderService implements IBotMailSenderService {
    private final IUserService userService;
    private final JavaMailSender mailSender;
    private static String dayOfWeek[] = {
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
        String text[] = this.createText(reservationInfo);

        System.out.println(text[0]);
        System.out.println(text[1]);

        mailMessage.setFrom(serviceUsername);
        mailMessage.setTo(userService.getById(reservationInfo.getCurrentUserId()).getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(text[0]);



        mailSender.send(mailMessage);
        if(reservationInfo.getUsersId() != null &&
                !reservationInfo.getUsersId().isEmpty()){
            for(Long userId : reservationInfo.getUsersId()){
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

        StringBuffer bufferDays = new StringBuffer("");
        if(reservationInfo.getWeekDay() != null && reservationInfo.getWeekDay().length != 0) {
            bufferDays.append("Days:\n");
            for(int item: reservationInfo.getWeekDay()){
                bufferDays.append(dayOfWeek[item]);
            }
        }

        String inf =  String.format(reservationInfo.getPlaceName() + "" +
                "\nFloor: " + reservationInfo.getFloorNumber() + "\nStart date: " +
                reservationInfo.getStartDate() + "\nEndDate: " + reservationInfo.getEndDate()
                + "\nStart time" + reservationInfo.getStartTime() + "\nEnd time" +
                reservationInfo.getEndTime() + "\n" + bufferDays.toString());


        StringBuffer mainPersonMessage = new StringBuffer("");
        if (reservationInfo.getHostId() == null
                || reservationInfo.getHostId().equals(reservationInfo.getReservationId())) {
             mainPersonMessage.append("You book place: " + inf);
        }
        else{
            mainPersonMessage.append("You invited for this: " + inf);
        }

        String result[] = {
                mainPersonMessage.toString(),
                String.format("You invited for this: " + inf)
        };
        return result;
    }
}
