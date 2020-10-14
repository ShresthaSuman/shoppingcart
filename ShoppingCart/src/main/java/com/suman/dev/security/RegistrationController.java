package com.suman.dev.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suman.dev.entity.User;
import com.suman.dev.repository.UserRepository;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping
	public String register(User user) {
		return "register";
	}
	
	@PostMapping("/newRegister")
	public String register(@Valid User user, BindingResult bind, Model model) {
		
		if(bind.hasErrors()) {
			return "register";
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			model.addAttribute("passwordNotMatch","The password you enter above didnot match with this password");
			return "register";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		
		userRepo.save(user);
		return "redirect:/login";
	}
}
