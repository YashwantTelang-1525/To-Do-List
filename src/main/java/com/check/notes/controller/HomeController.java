package com.check.notes.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.check.notes.model.UserDtls;
import com.check.notes.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/signin")
	public String signin() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/createUser")
	public String createUser(@ModelAttribute UserDtls user, HttpSession session, Model model) {
		System.out.println("endpoint hitted"); 
		boolean emailExists = userService.checkEmail(user.getEmail());
		 	System.out.println(user);
		    if (emailExists) {
		        session.setAttribute("msg", "Email ID already exists");
		    } else {
		        UserDtls userDtls = userService.createUser(user);
		        if (userDtls != null) {
		            session.setAttribute("msg", "User registered successfully");
		        } else {
		            session.setAttribute("msg", "Server error");
		        }
		    }

		    // Redirect to the register page with the message in the session
		    return "redirect:/register";
	}
}
