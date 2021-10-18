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
	
	//checks if the email exists in the database - usually for purposes of deduplication
	public Boolean emailExists(String email) {
		Boolean result = false;
		if(uRepo.findByUserEmail(email) != null) {
			result = true;
		}
		return result;
	}
	
	//Creates a new user and saves to the database
	public void newUser(User user) {
		String hashpassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashpassword);
		user.setType("customer"); //default permissions is customer
		uRepo.save(user);
	}
	
	//Checks hashed user password for login
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
	
	//Upon login, saves relevant data to Session
	public void login(HttpSession session, User user) {
		if(!(user.getType().equals("customer") || user.getType().equals("employee") || user.getType().equals("admin"))) {
			user.setType("customer"); //scrubs string value for permissions and resets to "customer" if invalid
		}
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
	
	//Clears session values upon logout
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
	
	//Checks if user is an employee
	public boolean isEmployee(HttpSession session) {
		if(session.getAttribute("permissions").equals("employee")) {
			return true;
		} else {
			return false;
		}
	}
	
	//Checks if user is an admin
	public boolean isAdmin(HttpSession session) {
		if(session.getAttribute("permissions").equals("admin")) {
			return true;
		} else {
			return false;
		}
	}
	
	//Upgrade user to Employee or Admin status permission is a string that must equal "customer", "employee" or "admin" (case sensitive"
	public Boolean promote(User user, String permission, HttpSession session) {
		if(isAdmin(session)) {
			if(!user.getType().equals("admin") && permission.equals("employee") && !user.getType().equals("employee")) {
				user.setType("employee");
				return true;
			} else if (!user.getType().equals("admin") && permission.equals("employee")) {
				user.setType("employee");
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//Update User
	public User updateUser(User user) {
		return this.uRepo.save(user);
	}

}
