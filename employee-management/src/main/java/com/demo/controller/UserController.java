package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.User;
import com.demo.repository.UserRepository;

@RestController
@RequestMapping(value="user")
public class UserController {

	/*@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;*/
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void saveUser(@RequestBody User user){
		PasswordEncoder encoder = 
		          PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println("Inside save user");
		userRepository.save(user);
	}
}
