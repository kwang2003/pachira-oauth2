package com.pachiraframework.oauth2.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author KevinWang
 *
 */
@Service
public class DefaultUserDetailsService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		if ("admin".equalsIgnoreCase(name)) {
			User user = mockUser();
			return user;
		}
		return null;
	}

	private User mockUser() {
		Collection<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("admin"));
		User user = new User("admin",new BCryptPasswordEncoder().encode("123456"),authorities);
		return user;
	}
}
