package com.suman.dev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userService;
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder
					.userDetailsService(userService)
					.passwordEncoder(encoder());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/category/**").hasAnyRole("ADMIN","USER")
			.antMatchers("/admin/**").hasAnyRole("ADMIN")
			.antMatchers("/").permitAll()
				.and().formLogin().loginPage("/login")
				.and().logout().logoutSuccessUrl("/")
				.and().exceptionHandling().accessDeniedPage("/");
	}
}
