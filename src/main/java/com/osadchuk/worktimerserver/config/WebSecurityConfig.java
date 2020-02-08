package com.osadchuk.worktimerserver.config;

import com.osadchuk.worktimerserver.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web Security configuration
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final MyBasicAuthenticationEntryPoint authenticationEntryPoint;

	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	public WebSecurityConfig(MyBasicAuthenticationEntryPoint authenticationEntryPoint,
	                         UserDetailsServiceImpl userDetailsService) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetailsService userDetailsServiceBean() {
		return userDetailsService;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/test", "/timer/login").permitAll()
				.antMatchers("/managers/status/check").hasRole("MANAGER")
				.antMatchers("/users/status/check").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.authenticationEntryPoint(authenticationEntryPoint);
	}
}