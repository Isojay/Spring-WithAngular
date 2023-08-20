package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.UserRepo;
import com.example.demo.dao.CustomerUserDetails;
import com.example.demo.dao.Users;

@Service
public class customUserDetailService implements UserDetailsService{
	
	
	@Autowired
	UserRepo userRepo;
	
	public customUserDetailService() {

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Users> users = userRepo.findUserByemail(email);
		users.orElseThrow(()-> new UsernameNotFoundException("Username not found"));
		return users.map(CustomerUserDetails::new).get();
	}


}
