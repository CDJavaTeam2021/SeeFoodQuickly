package com.seefoodquickly.twilio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration

public class TwilioInitializer {
	private final static Logger LOGGER = (Logger) LoggerFactory.getLogger(TwilioInitializer.class);
	private final TwilioConfiguration twilioConfiguration;
	
	@Autowired
	public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
		this.twilioConfiguration = twilioConfiguration;
		System.out.println(twilioConfiguration.getAccountSid());
		System.out.println(twilioConfiguration.getAuthToken());
		Twilio.init(
				twilioConfiguration.getAccountSid(),
				twilioConfiguration.getAuthToken()
				);
		LOGGER.info("Twilio initialized ... with account sid {} ", twilioConfiguration.getAccountSid());
	}
}
