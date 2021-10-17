package com.seefoodquickly.twilio;

public interface SmsSender {
	
//	void sendSms(SmsRequest smsRequest);
	void sendSms(String phoneNumber, String message);

}
