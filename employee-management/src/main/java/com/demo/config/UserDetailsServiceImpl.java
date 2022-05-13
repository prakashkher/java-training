package com.demo.config;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.model.User;
import com.demo.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	
	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}
	 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (null == user){
			throw new RuntimeException("User not found: " + username);
		}
		/*CustomUserDetails customUserDetail=new CustomUserDetails();
        customUserDetail.setUser(user);*/
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList());
	}

}
