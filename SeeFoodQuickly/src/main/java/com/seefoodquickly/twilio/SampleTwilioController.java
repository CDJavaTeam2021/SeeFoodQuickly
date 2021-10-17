package com.seefoodquickly.twilio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seefoodquickly.models.User;
import com.seefoodquickly.services.UserService;

@Controller
public class SampleTwilioController {
	private final UserService userService;
    
    public SampleTwilioController(UserService userService) {
    	this.userService = userService;
    }
        
    @RequestMapping(value="/twiliotest")
    public String twiliotest(@ModelAttribute("user") User user) {
        return "sampletwilio.jsp";
    }

    
}
