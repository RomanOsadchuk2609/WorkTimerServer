package com.osadchuk.worktimerserver.service;

import com.osadchuk.worktimerserver.entity.Role;
import com.osadchuk.worktimerserver.entity.User;
import com.osadchuk.worktimerserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * UserDetailsService implementation
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<User> optionalUser = userRepository.findByUsername(username);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.isEnabled(),
					true,
					true,
					true,
					getAuthoritiesByUser(user));
		} else {
			throw new UsernameNotFoundException("User not with username \"" + username + "\" found");
		}

	}

	private Set<GrantedAuthority> getAuthoritiesByUser(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		}
		LOGGER.debug("User {} authorities: {}", user.getUsername(), authorities);
		return authorities;
	}
}
