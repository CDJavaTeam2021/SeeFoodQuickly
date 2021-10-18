package com.seefoodquickly.twilio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class TwilioService {

    private final SmsSender smsSender;

    @Autowired
    public TwilioService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

//    public void sendSms(SmsRequest smsRequest) {
//        smsSender.sendSms(smsRequest);
//    }
    
    public void sendSms(String phoneNumber, String message) {
        smsSender.sendSms(phoneNumber, message);
    }

}