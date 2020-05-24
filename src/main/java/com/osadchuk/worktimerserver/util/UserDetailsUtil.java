/*
 * Copyright (c) 2020. Roman Osadchuk.
 */

package com.osadchuk.worktimerserver.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Util class for operations with UserDetails
 */
public class UserDetailsUtil {
	private UserDetailsUtil() {
	}

	/**
	 * @param userDetails instance of {@link UserDetails}
	 * @return true if {@link UserDetails#getAuthorities()} contains {@link GrantedAuthority#getAuthority()} equals ADMIN role
	 */
	public static boolean isAdmin(UserDetails userDetails) {
		return userDetails.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(WorkTimerConstants.Role.ADMIN::equals);
	}

}
