package com.seefoodquickly.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seefoodquickly.models.Item;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.models.User;
import com.seefoodquickly.services.OrderingService;
import com.seefoodquickly.services.ProductService;
import com.seefoodquickly.services.UserService;
import com.seefoodquickly.validators.UserValidator;

@Controller
public class HomeController {
	
	@Autowired
	UserService uServ;
	
	@Autowired
	UserValidator uVal;
	
	@Autowired
	ProductService pServ;
	
	@Autowired
	OrderingService oServ;

	@GetMapping("/") // Initial Router
	public String index(HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/login";
		} else {
			
			return "redirect:/menu";
		}
	}
	
	
	
	
	///////////////////////////////////////////////  GET REQUESTS  //////////////////////////////////////////
	
	
	// Login JSP
	@GetMapping("/login")
	public String login(HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "login.jsp";
		} else {
			
			return "redirect:/menu";
		}
	}
	
	
	// Registration JSP
	@GetMapping("/registration")
	public String registration(@ModelAttribute("user") User newUser, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "register.jsp";
		} else {
			
			return "redirect:/menu";
		}
	}
	
	
	
	// Order/Menu JSP
	@GetMapping("/menu")							
	public String menu(Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "menu.jsp";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			List<Product> allProducts = oServ.getAllProducts();
			model.addAttribute("allProducts", allProducts);
			
			return "menu.jsp";
		}
		
	}
	
	// Cart JSP
	@GetMapping("/cart")
	public String cart(Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			
			
			return "cart.jsp";
		}
	}
	
	//Checkout JSP
	@GetMapping("/checkout")
	public String checkout(Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			
			
			return "checkout.jsp";
		}
	}
	
	//Conformation JSP
	@GetMapping("/success")
	public String confirmation(Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			
			
			return "success.jsp";
		}
	}
	
	//All Orders JSP
	@GetMapping("/orders")
	public String orders(Model model, HttpSession session) {
			if(session.getAttribute("userId")==null) {
				return "redirect:/";
			} else {
				Long userId = (Long)session.getAttribute("userId");
				User loggedUser = this.uServ.findUserById(userId);
				model.addAttribute("loggedUser", loggedUser);
				
				
				
				return "orders.jsp";
			}
		}
	
	//Add Product JSP
	@GetMapping("/addProduct")

	public String addProduct(@ModelAttribute("product") Product product, Model model, HttpSession session) {

			if(session.getAttribute("userId")==null) {
				return "redirect:/";
			} else {
				Long userId = (Long)session.getAttribute("userId");
				User loggedUser = this.uServ.findUserById(userId);
				model.addAttribute("loggedUser", loggedUser);
				
				
				
				return "addProduct.jsp";
			}
		}
	
	//Remove from cart
	@GetMapping("/remove/{item_index}")
	public String removeItem(@PathVariable("item_index") String indexStr, HttpSession session) {
		oServ.removeFromCart(indexStr, session);
		return "redirect:/cart";
	}
	
	
	
	///////////////////////////////////////////////  POST REQUESTS  //////////////////////////////////////////
	
	// Login
	@PostMapping("/login")
	public String login(@RequestParam("email") String email, 
			@RequestParam("password") String password, 
			HttpSession session, 
			RedirectAttributes redAttr) {
		if(uServ.authenticateUser(email, password)) {
			User user = uServ.findUserByEmail(email);
			uServ.login(session, user);
			return "redirect:/menu";
		} else {
			redAttr.addFlashAttribute("errorString", "I'm sorry, I couldn't find that email/password combination");
			return "redirect:/login";		
		}
	}
	
	
	// Register
	@PostMapping("/register")
	public String registerPost(@Valid @ModelAttribute("user") User newUser, BindingResult result, HttpSession session) {
		uVal.validate(newUser, result);
		if(result.hasErrors()) {
			return "register.jsp";
		} else {
			uServ.newUser(newUser);
			uServ.login(session, newUser);
			return "redirect:/menu";
		}		
	}
	
	
	// Create Product
	@PostMapping("/addProduct")
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult result) {
			oServ.saveProduct(product);
		return "redirect:/menu";
	}
	
	// Add to Cart
	@PostMapping("/addItemToCart/{id}")
	public String addToCart(@PathVariable("id")Long id, @RequestParam("quantity") String quantity, HttpSession session) {
		this.oServ.addItemToCart(session, id, quantity);
		return "redirect:/menu";
	}
	
	
	
	
	///////////////////////////////////////////////  UTILITIES  //////////////////////////////////////////

	//Logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		uServ.logout(session);
		return "redirect:/";
	}
}
