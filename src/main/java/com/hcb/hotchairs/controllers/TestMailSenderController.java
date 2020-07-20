package com.hcb.hotchairs.controllers;

import com.hcb.hotchairs.services.impl.BotMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestMailSenderController {

    private final BotMailSenderService mailSender;

    @Autowired
    public TestMailSenderController(BotMailSenderService mailSender){
        this.mailSender = mailSender;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(){
        try {
            String sendTo = "ivan_kushner@mail.ru";
            String subject = "Test mailSender";
            String text = "success!";
            mailSender.send(sendTo, subject, text);
        }
        catch (Exception exception){
            // make something if mail wos not send
            return ResponseEntity.status(HttpStatus.CONFLICT).body("mail wasn't send");
        }
        return ResponseEntity.ok("eee boy!!!");
    }
}
