package com.example.springsecurity.controllers;

import com.example.springsecurity.model.User;
import com.example.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {

	@GetMapping("/user")
	public String openUserView(ModelMap model) {
		model.addAttribute("currentUser",
				(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return "user";
	}
}