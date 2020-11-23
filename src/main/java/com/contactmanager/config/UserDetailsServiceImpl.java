package com.contactmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.contactmanager.entities.User;
import com.contactmanager.repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		User user = userrepository.getUserByusername(username);
		if(user==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		
		CustomUserDetails userdetails= new CustomUserDetails(user);
		return userdetails;
	}

}
