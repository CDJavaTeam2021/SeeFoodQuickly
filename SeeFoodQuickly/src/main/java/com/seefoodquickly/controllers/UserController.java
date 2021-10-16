package com.seefoodquickly.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seefoodquickly.models.User;
import com.seefoodquickly.services.UserService;
import com.seefoodquickly.validators.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	UserService uServ;
	
	@Autowired
	UserValidator uVal;
	
	//Get and Post Mapping pair for registration using MVC forms
	@GetMapping("/register")
	public String registerGet(@ModelAttribute("newUser") User newUser) {
		return "register.jsp"; //TODO @James rename if necessary		
	}
	
	@PostMapping("/regiser")
	public String registerPost(@Valid @ModelAttribute("album") User newUser, BindingResult result, HttpSession session) {
		uVal.validate(newUser, result);
		if(result.hasErrors()) {
			return "register.jsp"; //TODO @James rename if necessary
		} else {
			uServ.newUser(newUser);
			uServ.login(session, newUser);
			return "redirect:/index"; //TODO @James redirect wherever makes sense
		}		
	}
	
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, 
			@RequestParam("password") String password, 
			HttpSession session, 
			RedirectAttributes redAttr) {
		if(uServ.authenticateUser(email, password)) {
			User user = uServ.findUserByEmail(email);
			uServ.login(session, user);
			return "redirect:/index"; //TODO @James redirect wherever makes sense
		} else {
			redAttr.addFlashAttribute("errorString", "I'm sorry, I couldn't find that email/password combination");
			return "redirect:/login"; //TODO @James redirect wherever makes sense			
		}
	}
	
	

}
