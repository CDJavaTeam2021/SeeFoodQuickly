package com.seefoodquickly.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.seefoodquickly.models.User;
import com.seefoodquickly.services.UserService;

@Component
public class UserValidator implements Validator{
	
	@Autowired
	UserService uServ;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    // 2
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            // 3
            errors.rejectValue("passwordConfirmation", "Match", "password does not match");
        }   
        
        if (uServ.emailExists(user.getUserEmail())) {
        	errors.rejectValue("username", "Match", "This user already exists");
        }
        
        if(user.getUserName().contains("<script")) {
        	errors.rejectValue("name", "Forbidden", "Don't do that!");
        }
        
        if(user.getUserEmail().contains("<script")) {
        	errors.rejectValue("username", "Forbidden", "Don't do that!");
        }
    }

}