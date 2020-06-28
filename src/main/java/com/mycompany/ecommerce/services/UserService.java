package com.mycompany.ecommerce.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.security.UserSS;

// ---------- BLL layer for User
@Service
public class UserService {
	
	// ---------- Returns user authenticated
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
