package com.seefoodquickly.twilio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sms")
public class TwilioController {

    private final TwilioService twServ;

    @Autowired
    public TwilioController(TwilioService twServ) {
        this.twServ = twServ;
    }

    @PostMapping
    public void sendSms(@Valid @RequestParam String phoneNumber, @RequestParam String message) {
    	twServ.sendSms(phoneNumber, message);
    }

}