package com.seefoodquickly.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seefoodquickly.models.Item;
import com.seefoodquickly.models.Order;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.models.User;
import com.seefoodquickly.repositories.ItemRepository;
import com.seefoodquickly.repositories.OrderRepository;
import com.seefoodquickly.repositories.ProductRepository;
import com.seefoodquickly.repositories.UserRepository;
import com.seefoodquickly.twilio.TwilioService;

@Service
public class OrderingService {
	
	ProductRepository pRepo;
	ItemRepository iRepo;
	OrderRepository oRepo;
	UserRepository uRepo;
	
	@Autowired
	UserService uServ;
	
	@Autowired
	TwilioService twServ;
	
	public OrderingService(ProductRepository pRepo, ItemRepository iRepo, OrderRepository oRepo, UserService uServ) {
		this.pRepo = pRepo;
		this.iRepo = iRepo;
		this.oRepo = oRepo;
		this.uServ = uServ;
	}
	
	//Product Methods
	
	//For new products using an MVC form
	public void saveProduct(Product product) {
		pRepo.save(product);
	}
	
	//for editing a product using a standard HTML Form
	//Since HTML forms send data back as Strings, this method
	//accepts the id as a String and converts it to a Long to 
	//access the Product in the database
	//Other attributes accepted as String and converted to applicable data types
	public void editProduct(String productIdStr, 
			String itemName, 
			String description, 
			String priceStr, 
			String makeMinutesStr) {
		Long productId = Long.valueOf(productIdStr);
		Product product = pRepo.findById(productId).get(); //assumes Id is always going to be valid
		product.setItemName(itemName);
		product.setDescription(description);
		product.setPrice(Float.valueOf(priceStr));
		product.setMakeMinutes(Integer.valueOf(makeMinutesStr));
		pRepo.save(product);
	}
	
	
	public List<Product> getAllProducts(){
		return (List<Product>) pRepo.findAll();
	}
	
	public Product getProductById(Long id) {
		return pRepo.findById(id).get();
	}
	
	//accepts ID as string assuming it's coming from
	//an HTML form
	public void deleteProduct(String productIdStr) {
		Long productId = Long.valueOf(productIdStr);
		Product product = pRepo.findById(productId).get(); //assumes ID is automatically valid
		pRepo.delete(product);
	}
	
	//overloaded delete method that accepts the Product
	public void deleteProduct(Product product) {
		pRepo.delete(product);
	}
	
	
	//Item Methods
	
	//saves item assuming MVC form is used
	public void saveItem(Item item) {
		iRepo.save(item);
	}
	
	//Remove an item from the cart, reset the indexes and lower cart total
	public void removeFromCart(String cartIndexStr, HttpSession session) {
		int cartIndex = Integer.valueOf(cartIndexStr);
		List<Item> cart = (List<Item>) session.getAttribute("myCart");
		float cost = cart.get(cartIndex).getLineTotal();
		float total = (float) session.getAttribute("cartTotal");
		total -= cost;
		session.setAttribute("cartTotal", total);
		cart.remove(cartIndex);
		resetCart(session);
	}
	
	//Reset Cart Data
	public void resetCart(HttpSession session) {
		if(session.getAttribute("myCart") == null) {
			List<Item> emptyCart = new ArrayList<Item>();
			session.setAttribute("myCart", emptyCart);
			session.setAttribute("cartTotal", 0f);
		} else {
			List<Item> cart = (List<Item>) session.getAttribute("myCart");
			Float newTotal = 0f;
			for(int i = 0; i < cart.size(); i++) {
				cart.get(i).setCartIndex(i);
				newTotal += cart.get(i).getLineTotal();
			}
			session.setAttribute("cartTotal", newTotal);
		}
		
	}
	
	//Assumes the only functionality needed is to change quantity
	public void updateQty(String cartIndexStr, String newQtyStr, HttpSession session) {
		if(Integer.valueOf(newQtyStr) == 0) {
			removeFromCart(cartIndexStr, session);			
		} else {
			Integer cartIndex = Integer.valueOf(cartIndexStr);
			int newQty = Integer.valueOf(newQtyStr);
			List<Item> myCart = (List<Item>) session.getAttribute("myCart");
			Item item = myCart.get(cartIndex);
			item.setQuantity(newQty);
			item.setLineTotal(newQty * item.getItemProduct().getPrice());
			myCart.set(cartIndex, item);
			session.setAttribute("myCart", myCart);
			resetCart(session);
		}
	}
	
	//accepts fields as Strings and converts as necessary
	public void addItemToCart(HttpSession session, 
			Long prodId,  
			String quantityS) {
		
		//converting String inputs into appropriate data types
		int quantity = Integer.valueOf(quantityS);
		
		//instantiating and populating item with data
		Item newItem = new Item();
		newItem.setItemProduct(getProductById(prodId));
		newItem.setQuantity(quantity);
		float cost = getProductById(prodId).getPrice();
		cost *= quantity;
		newItem.setLineTotal(cost);
		
		
		System.out.println("New Item Populated");
		
		//Adding item to cart
		if(session.getAttribute("myCart") == null) {
			List<Item> emptyCart = new ArrayList<Item>();
			session.setAttribute("myCart", emptyCart);
		}
		List<Item> myCart = (List<Item>) session.getAttribute("myCart");
		int cartIndex = myCart.size();
		newItem.setCartIndex(cartIndex);
		myCart.add(newItem);
		session.setAttribute("myCart", myCart);
		float total = (float) session.getAttribute("cartTotal");
		total += cost;
		session.setAttribute("cartTotal", total);	
			}

	
	
	//Order Methods
	
	//find by ID where ID is provided as a String
	public Order findOrderById(String orderIdStr) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).get();
		return order;
	}
	
	//overloaded to accept order ID as a Long
	public Order findOrderById(Long orderId) {
		Order order = oRepo.findById(orderId).get();
		return order;
	}
	
	//Returns null if order doesn't exist
	public Order safelyFindOrderById(String orderIdStr) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).orElse(null);
		return order;
	}
	
	public List<Order> getAllOrders() {
		return (List<Order>) oRepo.findAll();
	}
	
	//Returns list of only the open orders which SHOULD be oldest first
	public List<Order> getOpenOrders(){
		return oRepo.findByOrderOpenIsOrderByCreatedAt(true);
	}
	
	//Returns list of all orders ordered by most recent
	public List<Order> getAllOrdersRecentFirst(){
		return oRepo.findAllByOrderByCreatedAt();
	}
	
	//Returns orders belonging to specific customer ordered by most recent first
	public List<Order> myOrders(String userIdStr){
		Long userId = Long.valueOf(userIdStr);
		User user = uRepo.findById(userId).get();
		return oRepo.findByCustomerOrderByCreatedAt(user);
	}
	
	public List<Order> myOrders(Long userId){
		User user = uRepo.findById(userId).get();
		return oRepo.findByCustomerOrderByCreatedAt(user);
	}
	
	public List<Order> myOrders(User user){
		return oRepo.findByCustomerOrderByCreatedAt(user);
	}

	
	//Method takes in session to determine who is ordering and takes in orderEmail and orderPhone
	//in case User wants to override default contact
	public Order checkout(HttpSession session) {
		
		Order order = new Order();
		List<Item> newItems = (List<Item>) session.getAttribute("myCart");
		order.setItems(newItems);
		order.setSubtotal((float) session.getAttribute("cartTotal"));
		order.setTax((float) (order.getSubtotal()*.0925));
		order.setTotal(((float)(order.getSubtotal()*1.0925)));
		oRepo.save(order);
		for(Item item : newItems) {
			item.setParentOrder(order);
			//item.setItemStatus("New");  //Do we want an item status too? -S. Yee
			iRepo.save(item);
		}
		
		order.setStatus("New");
		String orderId = String.valueOf(order.getId());
		String zeros = "";
		for(int i = 0; i < 6 - orderId.length(); i++) {
			zeros += "0";
		}
		String orderNumber = "ORD";
		orderNumber += zeros;
		orderNumber += orderId;
		order.setOrderNumber(orderNumber);
		
		// add contact info from customer
		order.setOrderEmail((String)session.getAttribute("myEmail"));
		order.setOrderPhone((String)session.getAttribute("myPhone"));
		// 
				
		order.setOrderOpen(true);
				
		Long userId = (Long) session.getAttribute("userId");
		order.setCustomer(uServ.findUserById(userId));
		oRepo.save(order);
				
		emptyCart(session);
				
		return order;
		
	}
	
	public String confirmOrder(String orderIdStr, String phone) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).get(); //Assumes already valid ID
		order.setStatus("Confirmed");
		////  send Twilio confirmation that order is being prepared
		String message = order.getCustomer().getUserName() + ", we are starting to prepare your order and it will be ready in 10 minutes";
    	twServ.sendSms(phone, message);		
		
		oRepo.save(order);
		return order.getOrderNumber(); //returns order number
	}
	
	public void completeOrder(String orderIdStr, String phone) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).get(); //assumes id is correct
		order.setStatus("Finished");
		order.setOrderOpen(false);
		////  send Twilio confirmation that order is complete
		String message = order.getCustomer().getUserName() + ", your order is completed!";
    	twServ.sendSms(phone, message);		
		oRepo.save(order);
	}
	
	//Empty the cart
	public void emptyCart(HttpSession session) {
		List<Item> empty = new ArrayList<Item>();
		session.setAttribute("myCart", empty);
		session.setAttribute("cartTotal", 0f);
	}
	
	
	
	

	

}
