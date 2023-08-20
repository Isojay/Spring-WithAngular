package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Repo.RoleRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.dao.Roles;
import com.example.demo.dao.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class loginController {
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;
	
	
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	@GetMapping("/register")
	public String showRegister() {
		return "register";
	}
	@PostMapping("/register")
	public String Doregister(@ModelAttribute("user") Users users, HttpServletRequest request) throws ServletException{
		String password = users.getPassword();
		users.setPassword(bCryptPasswordEncoder.encode(password));
		List<Roles> roles = new ArrayList<>();
		roles.add(roleRepo.findById(2).get());
		users.setRoles(roles);
		userRepo.save(users);
		request.login(users.getEmail(), password);
		
		
		return "redirect:/";
		
	}
	
}
