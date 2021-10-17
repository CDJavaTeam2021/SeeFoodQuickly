package com.seefoodquickly.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seefoodquickly.models.Item;
import com.seefoodquickly.models.Order;
import com.seefoodquickly.models.Product;
import com.seefoodquickly.repositories.ItemRepository;
import com.seefoodquickly.repositories.OrderRepository;
import com.seefoodquickly.repositories.ProductRepository;
import com.seefoodquickly.repositories.UserRepository;

@Service
public class OrderingService {
	
	ProductRepository pRepo;
	ItemRepository iRepo;
	OrderRepository oRepo;
	UserRepository uRepo;
	
	@Autowired
	UserService uServ;
	
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
	
	
	public void removeFromCart(String cartIndexStr, HttpSession session) {
		int cartIndex = Integer.valueOf(cartIndexStr);
		List<Item> cart = (List<Item>) session.getAttribute("myCart");
		cart.remove(cartIndex);
	}
	
	//Assumes the only functionality needed is to change quantity
		public void updateQty(String cartIndexStr, String newQtyStr, HttpSession session) {
			Integer cartIndex = Integer.valueOf(cartIndexStr);
			int newQty = Integer.valueOf(newQtyStr);
			List<Item> myCart = (List<Item>) session.getAttribute("myCart");
			Item item = myCart.get(cartIndex);
			item.setQuantity(newQty);
			myCart.set(cartIndex, item);
			session.setAttribute("myCart", myCart);
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
	
	public List<Order> getAllOrders() {
		return (List<Order>) oRepo.findAll();
	}
	
	//Returns list of only the open orders which SHOULD be oldest first
	public List<Order> getOpenOrders(){
		return oRepo.findByOrderOpenIsOrderByCreatedAt(true);
	}

	
	//Method takes in session to determine who is ordering and takes in orderEmail and orderPhone
	//in case User wants to override default contact
	public Order checkout(HttpSession session, String orderEmail, String orderPhone) {
		
		Order order = new Order();
		List<Item> newItems = (List<Item>) session.getAttribute("myCart");
		order.setItems(newItems);
		order.setTotal((float) session.getAttribute("cartTotal"));
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
		order.setOrderEmail(orderEmail);
		order.setOrderPhone(orderPhone);
		// 
				
		order.setOrderOpen(true);
				
		Long userId = (Long) session.getAttribute("userId");
		order.setCustomer(uServ.findUserById(userId));
		oRepo.save(order);
				
		emptyCart(session);
				
		return order;
		
	}
	
	public String confirmOrder(String orderIdStr) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).get(); //Assumes already valid ID
		order.setStatus("Confirmed");
		//TODO add text notification function here
		oRepo.save(order);
		return order.getOrderNumber(); //returns order number
	}
	
	public void completeOrder(String orderIdStr) {
		Long orderId = Long.valueOf(orderIdStr);
		Order order = oRepo.findById(orderId).get(); //assumes id is correct
		order.setStatus("Finished");
		order.setOrderOpen(false);
		//TODO add test notification here
		oRepo.save(order);
	}
	
	//Empty the cart
	public void emptyCart(HttpSession session) {
		List<Item> empty = new ArrayList<Item>();
		session.setAttribute("myCart", empty);
		session.setAttribute("cartTotal", 0f);
	}
	
	
	
	

	

}
