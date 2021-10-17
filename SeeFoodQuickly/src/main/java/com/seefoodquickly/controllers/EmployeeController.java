package com.seefoodquickly.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.seefoodquickly.models.Product;
import com.seefoodquickly.services.OrderingService;

@Controller
public class EmployeeController {
	
	@Autowired
	OrderingService oServ;
	
	@GetMapping("/products/new")
	public String newProductPage(@ModelAttribute("newProduct") Product newProduct) {
		return "/testJSP/newproductTest.jsp";
	}
	
	@PostMapping("/products/new")
	public String saveNewProduct(@Valid @ModelAttribute("newProduct") Product newProduct, BindingResult result) {
		if(result.hasErrors()) {
			return "/testJSP/newproductTest.jsp";
		} else {
			oServ.saveProduct(newProduct);
			return "redirect:/products/new";
		}
	}

}
