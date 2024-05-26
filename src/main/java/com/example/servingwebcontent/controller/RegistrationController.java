package com.example.servingwebcontent.controller;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import com.example.servingwebcontent.repos.UserRepository;
import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.domain.Role;



@Controller
public class RegistrationController {
	
	
	
	@Autowired
	private UserRepository userRepo;
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	@PostMapping("/registration")
	public String addUser(Model model,User user) {
		User userFromDb=userRepo.findByUsername(user.getUsername());
		if(userFromDb!=null) {
			model.addAttribute("message","User exists!");
			return "registration";
		}
		user.setActive(true);
		user.setRoles(Collections.singleton(Role.USER));
		userRepo.save(user);
		
		return "redirect:/login";
	}
}
