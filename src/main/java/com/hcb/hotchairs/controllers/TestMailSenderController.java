package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.impl.BotMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mail")
public class TestMailSenderController {

    private final BotMailSenderService mailSender;

    @Autowired
    public TestMailSenderController(BotMailSenderService mailSender){
        this.mailSender = mailSender;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestParam(value = "email",defaultValue = "ivan_kushner@mail.ru") String sendTo,
                                              @RequestParam(value = "subject", defaultValue = "Your booking") String subject,
                                              @RequestParam(value = "text", defaultValue = "Place booked successfully") String text) {
        try {
            mailSender.send(sendTo, subject, text);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("mail wasn't send");
        }
        return ResponseEntity.ok("sent");
    }
}
