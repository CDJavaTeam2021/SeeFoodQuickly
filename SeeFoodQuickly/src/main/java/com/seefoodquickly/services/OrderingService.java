package com.seefoodquickly.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seefoodquickly.models.Item;
import com.seefoodquickly.models.Order;
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
	
	
	
	//Item Methods
	
	
	//Order Methods
	public List<Order> getAllOrders() {
		return (List<Order>) oRepo.findAll();
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
	
	//Empty the cart
	public void emptyCart(HttpSession session) {
		List<Item> empty = new ArrayList<Item>();
		session.setAttribute("myCart", empty);
		session.setAttribute("cartTotal", 0f);
	}
	
	

	

}
