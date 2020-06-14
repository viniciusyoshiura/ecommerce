package com.mycompany.ecommerce.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.ecommerce.dto.CredentialsDTO;

// ---------- Authentication filter that intercepts login request (127.0.0.1:8088/login)
// ---------- Extending UsernamePasswordAuthenticationFilter will intercepts login request
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTUtils jwtUtils;

	// ---------- Injecting from constructor
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			// ---------- Getting data from request and convert to CredentialsDTO
			CredentialsDTO credentials = new ObjectMapper().readValue(req.getInputStream(), CredentialsDTO.class);

			// --------- Instantiating UsernamePasswordAuthenticationToken from Spring
			// Security
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

			// -------- Checking if username and passwords are valid
			// -------- Spring uses UserDetailsServiceImpl
			// -------- Authentication informs if authentication has succeeded or not
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// ---------- If authentication has succeeded
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		// ---------- Get principal return user from Spring Security
		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtils.generateToken(username);
		// ---------- Generating token and adding into header response
		res.addHeader("Authorization", "Bearer " + token);

	}

	// ---------- Set http response status to 401, when user was not authenticated
	// ---------- Since Spring Security returns 403
	// ---------- Customizing when authentication failure
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
		// ---------- Customizing when authentication failure
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Unauthorized\", "
					+ "\"message\": \"Invalid email or password\", " + "\"path\": \"/login\"}";
		}
	}

}
