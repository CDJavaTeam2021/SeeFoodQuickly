package com.seefoodquickly.stripe;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("stripe")

public class StripeConfiguration {
	private String pub_key;
	private String sec_key;
	
	public StripeConfiguration() {
		
	}
	
	public String getPub_key() {
		return pub_key;
	}
	public void setPub_key(String pub_key) {
		this.pub_key = pub_key;
	}
	public String getSec_key() {
		return sec_key;
	}
	public void setSec_key(String sec_key) {
		this.sec_key = sec_key;
	}
	
	

}
