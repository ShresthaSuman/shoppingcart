package com.suman.dev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.suman.dev.entity.Admin;
import com.suman.dev.entity.User;
import com.suman.dev.repository.AdminRepository;
import com.suman.dev.repository.UserRepository;

@Service
public class UserDetailServiceRepository implements UserDetailsService{

	@Autowired
	UserRepository userRepo;
	@Autowired
	AdminRepository adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	    User user=userRepo.findByUsername(username);
	    Admin admin=adminRepo.findByUsername(username);
	    
	    if(user != null) {
	    	return user;
	    }
	    if(admin != null) {
	    	return admin;
	    }
		throw new UsernameNotFoundException(username +" not found");
		
	}

}
