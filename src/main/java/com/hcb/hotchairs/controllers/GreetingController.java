package com.hcb.hotchairs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    @ResponseBody
    @RequestMapping("/")
    public String greeting() {
        return "Greetings, traveler!";
    }
}
