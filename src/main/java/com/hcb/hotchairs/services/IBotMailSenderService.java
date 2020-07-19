package com.hcb.hotchairs.services;

public interface IBotMailSenderService {
    void send(String sendTo,String subject, String text);
}
