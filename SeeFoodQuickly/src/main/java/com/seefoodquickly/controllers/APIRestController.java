package com.seefoodquickly.controllers;

import java.util.HashMap;

import com.seefoodquickly.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API")
public class APIRestController {
	
	@Autowired
	UserService uServ;
	
	//Test Output APIs
	@RequestMapping("/StringTest")
	public String stringTest() {
		return "This is a string test, hello!";
	}
	
	/*
	Coupled with an async JavaScript function on the front end, we can use HashMap API calls
	To refresh data within the page without having to refresh the page.
	An example JavaScript code snippet to call the API and retrieve as a JSON:
		var response = await fetch([API address]);
		var data = await response.json();
	-Stuart Yee
	*/
	
	@RequestMapping("/mapTest")
	public HashMap<String, String> mapTest(){
		HashMap<String, String> output = new HashMap<String, String>();
		output.put("Test1", "one");
		output.put("Test2", "two");
		
		return output;
	}

}
