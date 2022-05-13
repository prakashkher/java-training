package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {
	public static final String SIGN_UP_URL = "/user/sign-up";	
	private UserDetailsServiceImpl userDetailsService;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;
	PasswordEncoder encoder = 
	          PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	public BasicConfiguration(UserDetailsServiceImpl userDetailsService) {
	    super();
	    this.userDetailsService = userDetailsService;
	    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	auth
          .inMemoryAuthentication()
          .withUser("user")
          .password(encoder.encode("password"))
          .roles("USER")
          .and()
          .withUser("admin")
          .password(encoder.encode("admin"))
          .roles("USER", "ADMIN");
    	auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    	
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
        /*http
          .authorizeRequests()
          .anyRequest()
          .authenticated()
          .and()
          .httpBasic().and().csrf().disable()
          .addFilter(new JWTAuthenticationFilter(authenticationManager()))
          .addFilter(new JWTAuthorizationFilter(authenticationManager()));*/
        http.headers().frameOptions().disable();
    }
}