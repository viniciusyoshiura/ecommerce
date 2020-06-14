package com.mycompany.ecommerce.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//---------- Authorization filter that intercepts requests
//---------- Extending UsernamePasswordAuthenticationFilter will intercepts login request
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private JWTUtils jwtUtils;

	private UserDetailsService userDetailService;

	// ---------- UserDetailService to check if token is valid
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils,
			UserDetailsService userDetailService) {
		super(authenticationManager);
		this.jwtUtils = jwtUtils;
		this.userDetailService = userDetailService;

	}

	// ---------- Standard Method of Spring
	// ---------- Intercepts request before continuing
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException, AccessDeniedException {

		// ---------- Getting Authorization (token) from header request
		String authorizationHeader = request.getHeader("Authorization");

		// ---------- Checking if token is valid
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			// ---------- Check if token is valid and returns
			// UsernamePasswordAuthenticationToken from Spring Security
			// ---------- Token is value after "Bearer"
			UsernamePasswordAuthenticationToken auth = getAuthentication(authorizationHeader.substring(7));
			if (auth != null) {
				// ---------- Access granted
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		// ---------- Continue request
		try {
			chain.doFilter(request, response);
		} catch (AccessDeniedException e) {
			e.printStackTrace();
		}
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (jwtUtils.validToken(token)) {
			String username = jwtUtils.getUserName(token);
			UserDetails user = userDetailService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		}
		return null;
	}


}
