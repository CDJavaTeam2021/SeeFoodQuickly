package com.seefoodquickly.controllers;

import java.util.Date;
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

import com.seefoodquickly.models.Order;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.models.User;
import com.seefoodquickly.services.OrderingService;
import com.seefoodquickly.services.ProductService;
import com.seefoodquickly.services.UserService;
import com.seefoodquickly.validators.UserValidator;
import com.seefoodquickly.twilio.TwilioService;

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
	
	@Autowired
	TwilioService twServ;

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
			oServ.resetCart(session);
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			oServ.resetCart(session);
			
			Date newDate = new Date();
			model.addAttribute("currentDate", newDate);
			
			
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
	
	//Confirmation JSP
	@GetMapping("/success/{orderNum}")
	public String confirmation(Model model, 
			HttpSession session, 
			@PathVariable("orderNum") String orderIdStr) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			Order order = oServ.findOrderById(orderIdStr);
			model.addAttribute("loggedUser", loggedUser);
			model.addAttribute("newOrder", order);
			
			
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
				if (loggedUser.getType().equals("customer")) {
					return "redirect:/my_orders";
				} else {
					model.addAttribute("loggedUser", loggedUser);
					model.addAttribute("orderList", oServ.getAllOrdersRecentFirst());			
					return "orders.jsp";
				}				
			}
		}
	
	@GetMapping("/orders/open")
	public String queue(Model model, HttpSession session) {
		if(session.getAttribute("permissions").equals("employee") || session.getAttribute("permissions").equals("admin")) {
			User loggedUser = uServ.findUserById((Long) session.getAttribute("userId"));
			model.addAttribute("loggedUser", loggedUser);
			model.addAttribute("orderList", oServ.getOpenOrders());			
			return "orders.jsp";
		} else {
			return "redirect:/";
		}
	}
	
	//View specific Order
	@GetMapping("/orders/{order_id}")
	public String viewOrder(@PathVariable("order_id") String orderIdStr, Model model, HttpSession session) {
		Order order = oServ.safelyFindOrderById(orderIdStr);
		if(order == null) {
			return "redirect:/my_orders";
		} 
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			if(order.getCustomer().getId() == loggedUser.getId() || loggedUser.getType().equals("employee") || loggedUser.getType().equals("admin")) {
				model.addAttribute("loggedUser", loggedUser);
				model.addAttribute("thisOrder", order);
				return "/orderView.jsp";
			}
			return "redirect:/orders";
		}	
	}
	
	//Add Product JSP
	@GetMapping("/addProduct")

	public String addProduct(@ModelAttribute("product") Product product, Model model, HttpSession session) {

			if(session.getAttribute("userId")==null || session.getAttribute("permissions").equals("customer")) {
				return "redirect:/";
			} else {
				Long userId = (Long)session.getAttribute("userId");
				User loggedUser = this.uServ.findUserById(userId);
				model.addAttribute("loggedUser", loggedUser);
				
				
				
				return "addProduct.jsp";
			}
		}
	
	//MyOrderHistory
	@GetMapping("/my_orders")
	public String getMyOrders(HttpSession session, Model viewModel) {
		Long userId = (Long)session.getAttribute("userId");
		User loggedUser = this.uServ.findUserById(userId);
		viewModel.addAttribute("loggedUser", loggedUser);
		viewModel.addAttribute("orderList", oServ.myOrders(loggedUser));
		return "customerOrders.jsp";
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
	
	//Remove Item from Cart
		@GetMapping("remove/{cartIndex}")
		public String removeItem(@PathVariable("cartIndex") String index, HttpSession session) {
			oServ.removeFromCart(index, session);
			return "redirect:/cart";
		}
		
	//Checkout and create new order
		@PostMapping("/checkout")
		public String newOrder(HttpSession session) {
			Order order = oServ.checkout(session);
			
			////  send twilio confirmation that order is received
			String userName = (String)session.getAttribute("userName");
			String userPhone = (String)session.getAttribute("userPhone");
			
			System.out.println("in home controller post checkout, user is: " + userName);
			System.out.println("in home controller post checkout, phone is: " + userPhone);

			
			return "redirect:/success/" +order.getId();
		}
		
		@PostMapping("/update/contact")
		public String updateContact(@RequestParam("myPhone") String newPhone, HttpSession session) {
			session.setAttribute("myPhone", newPhone);
			return "redirect:/cart";
		}
		
		@PostMapping("/orders/confirm/{order_id}")
		public String confirmOrder(@RequestParam("phone") String confirmPhone, 
				HttpSession session,
				@PathVariable("order_id") String orderIdStr) {
			oServ.confirmOrder(orderIdStr, confirmPhone);
			return "redirect:/orders/open";
		}
		
		@PostMapping("/orders/complete/{order_id}")
		public String completeOrder(@RequestParam("phone") String notifyPhone, 
				HttpSession session,
				@PathVariable("order_id") String orderIdStr) {
			oServ.completeOrder(orderIdStr, notifyPhone);
			return "redirect:/orders/open";
		}
	
	
	///////////////////////////////////////////////  UTILITIES  //////////////////////////////////////////


	
	
	//Logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		uServ.logout(session);
		return "redirect:/";
	}
}
