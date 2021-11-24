package com.seefoodquickly.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seefoodquickly.models.Order;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.models.User;
import com.seefoodquickly.services.OrderingService;
import com.seefoodquickly.services.ProductService;
import com.seefoodquickly.services.UserService;
import com.seefoodquickly.twilio.TwilioService;
import com.seefoodquickly.validators.UserValidator;

@Controller
public class HomeController {
	@Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;
	
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
	
	public static String FOLDERPATH = "src/main/resources/static/images/products/";
	public static String SHORTPATH = "images/products/";

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
	
	//Static about page
	@GetMapping("/about")
	public String aboutPage(HttpSession session, Model model) {
		if(session.getAttribute("userId") != null) {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			model.addAttribute("userId", userId);
		}
		return "about.jsp";
	}
	
	
	
	// Menu JSP
	@GetMapping("/menu")							
	public String menu(Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "menu.jsp";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			model.addAttribute("userId", userId);
			
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
	        model.addAttribute("stripePublicKey", stripePublicKey);

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
	
	//Confirmation JSP
//	@GetMapping("/success")
//	public String confirmation(Model model, 
//			HttpSession session) {
//		if(session.getAttribute("userId")==null) {
//			return "redirect:/";
//		} else {
//			Long userId = (Long)session.getAttribute("userId");
//			User loggedUser = this.uServ.findUserById(userId);
//			model.addAttribute("loggedUser", loggedUser);
//			
//			
//			return "success.jsp";
//		}
//	}
	
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
					
					//For styling the same JSP with two different Requests
					String title="All Orders";
					model.addAttribute("title", title);
					return "orders.jsp";
				}				
			}
		}
	
	
	//Open Order NEW
	@GetMapping("/orders/open")
	public String queue(Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			if (loggedUser.getType().equals("customer")) {
				return "redirect:/";
			} else {
				model.addAttribute("loggedUser", loggedUser);
				model.addAttribute("orderList", oServ.getOpenOrders());		
				
				//For styling the same JSP with two different Requests
				String title="Open Orders";
				model.addAttribute("title", title);
				return "orders.jsp";
			}				
		}
	}
	
//	//Open Order
//	@GetMapping("/orders/open")
//	public String queue(Model model, HttpSession session) {
//		if(session.getAttribute("permissions").equals("employee") || session.getAttribute("permissions").equals("admin")) {
//			User loggedUser = uServ.findUserById((Long) session.getAttribute("userId"));
//					
//			return "orders.jsp";
//		} else {
//			return "redirect:/";
//		}
//	}
	
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
	
	// View Profile
	@GetMapping("/profile/{id}")
	public String viewProfile(@PathVariable("id")Long id, Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			User user = this.uServ.findUserById(id);
			model.addAttribute("user", user);
			
			
			return "viewProfile.jsp";
		}
	}
	
	
	// Edit Profile
		@GetMapping("/profile/{id}/edit")
	public String editProfile(@PathVariable("id")Long id, Model model, HttpSession session) {
		if(session.getAttribute("userId")==null) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			model.addAttribute("loggedUser", loggedUser);
			
			User user = this.uServ.findUserById(id);
			model.addAttribute("user", user);
		
			return "editProfile.jsp";
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

				if(loggedUser.getType().equals("customer")) {
					return "redirect:/";
				} else {
					model.addAttribute("loggedUser", loggedUser);
				}
				return "addProduct.jsp";
			}
		}
	
	//Add image to product
	@GetMapping("/addPicture/{prod_id}")
	public String addPicture(HttpSession session, Model model, @PathVariable("prod_id") String prodIdStr) {
		if(session.getAttribute("userId")==null || session.getAttribute("permissions").equals("customer")) {
			return "redirect:/";
		} else {
			Long userId = (Long)session.getAttribute("userId");
			User loggedUser = this.uServ.findUserById(userId);
			Long prodId = Long.valueOf(prodIdStr);
			Product product = oServ.getProductById(prodId);
			model.addAttribute("loggedUser", loggedUser);
			model.addAttribute("newProduct", product);
			return "addPicture.jsp";
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
	
	// Edit Profile
	@PostMapping("/editProfile/{id}")
	public String updateProfile(@PathVariable("id")Long id, @RequestParam("userName") String userName, @RequestParam("userEmail") String userEmail,
								@RequestParam("userPhone") String userPhone, @RequestParam("type") String type, 
								Model model, HttpSession session) {
		User user = uServ.findUserById(id);
		user.setUserName(userName);
		user.setUserEmail(userEmail);
		user.setUserPhone(userPhone);
		user.setType(type);
		this.uServ.updateUser(user);

		return "redirect:/profile/{id}";
	}
	
	
	// Create Product
	@PostMapping("/addProduct")
	public String addProduct(@Valid 
			@ModelAttribute("product") Product product, 
			BindingResult result) {
			oServ.saveProduct(product);
		return "redirect:/addPicture/" + product.getId();
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
		@GetMapping("/checkedout")
		public String newOrder(HttpSession session) {
			Order order = oServ.checkout(session);
			////  send Twilio confirmation that order is received
			String phoneNumber = order.getOrderPhone();
			String message = "Thank you for your order " + order.getCustomer().getUserName() +
					"!  We have received your order number " + order.getOrderNumber();
	    	twServ.sendSms(phoneNumber, message);
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
		
		@PostMapping("/addPicture/{prod_id}")
		public String uploadPicture(@PathVariable("prod_id") String prodIdStr, @RequestParam("image") MultipartFile file) {
			Long prodId = Long.valueOf(prodIdStr);
			Product product = oServ.getProductById(prodId);
			if(file.isEmpty()) {
				return "redirect:/addProduct";
			}
			try {
				System.out.println("getting the file");
				byte[] bytes = file.getBytes();
				Path path = Paths.get(FOLDERPATH + file.getOriginalFilename());
				Files.write(path, bytes);
				String url = SHORTPATH + file.getOriginalFilename();
				System.out.println(url);
				oServ.addPicture(url, "Picture of " + product.getItemName() , product);				
				return "redirect:/addProduct";
			} catch(Exception e) {
				return "redirect:/addPicture/{prod_id}";
			}
			
			
		}
	
	
	///////////////////////////////////////////////  UTILITIES  //////////////////////////////////////////


	
	
	//Logout
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		uServ.logout(session);
		return "redirect:/";
	}
}
