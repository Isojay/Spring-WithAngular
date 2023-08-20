package com.example.demo.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.Repo.RoleRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.dao.Roles;
import com.example.demo.dao.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SucessHandler implements AuthenticationSuccessHandler{
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email =  token.getPrincipal().getAttributes().get("email").toString();
		if (!userRepo.findUserByemail(email).isEmpty()) {
			Users users = new Users();
			users.setFirstname(token.getPrincipal().getAttributes().get("given_name").toString());
			users.setLastname(token.getPrincipal().getAttributes().get("family_name").toString());
			users.setEmail(email);
			List<Roles> roles = new ArrayList<>();
			roles.add(roleRepo.findById(2).get());
			users.setRoles(roles);
			userRepo.save(users);
		}
		redirectStrategy.sendRedirect(request, response, "/");
	}
}
