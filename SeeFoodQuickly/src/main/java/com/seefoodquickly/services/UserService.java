package com.seefoodquickly.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.seefoodquickly.models.Item;
import com.seefoodquickly.models.User;
import com.seefoodquickly.repositories.UserRepository;


@Service
public class UserService {
	
	UserRepository uRepo;

	public UserService(UserRepository uRepo) {
		this.uRepo = uRepo;
	}
	
	
	//Business logic methods
	
	public Boolean emailExists(String email) {
		Boolean result = false;
		
		if(uRepo.findByUserEmail(email) != null) {
			result = true;
		}
		
		return result;
	}
	
	public void newUser(User user) {
		String hashpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpassword);
		user.setType("customer");
		uRepo.save(user);
	}
	
	public boolean authenticateUser(String email, String password) {
		if(! emailExists(email)){
			return false;
		} else {
			User user = uRepo.findByUserEmail(email);
			if(BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public User findUserByEmail(String email) {
		if(emailExists(email)) {
			return uRepo.findByUserEmail(email);
		} else {
			return null;
		}
	}
	
	public User findUserById(Long id) {
		return uRepo.findById(id).get();
	}
	
	public void login(HttpSession session, User user) {
		List<Item> cart = new ArrayList<Item>();
		session.setAttribute("userId", user.getId());
		session.setAttribute("userName", user.getUserName());
		session.setAttribute("permissions", user.getType());
		session.setAttribute("loggedIn", true);
		session.setAttribute("myCart", cart);
		session.setAttribute("cartTotal", 0f);
		session.setAttribute("myPhone", user.getUserPhone());
		session.setAttribute("myEmail", user.getUserEmail());
	}
	
	public void logout(HttpSession session) {
		session.setAttribute("userId", null);
		session.setAttribute("userName", "Guest");
		session.setAttribute("permissions", "customer");
		session.setAttribute("loggedIn", false);
		session.setAttribute("myCart", null);
		session.setAttribute("cartTotal", 0f);
		session.setAttribute("myPhone", "");
		session.setAttribute("myEmail", "");
	}
	
	public boolean isEmployee(HttpSession session) {
		if(session.getAttribute("permissions").equals("employee")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAdmin(HttpSession session) {
		if(session.getAttribute("permissions").equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

}
