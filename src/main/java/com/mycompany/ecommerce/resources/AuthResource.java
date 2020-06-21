package com.mycompany.ecommerce.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.ecommerce.dto.EmailDTO;
import com.mycompany.ecommerce.security.JWTUtils;
import com.mycompany.ecommerce.security.UserSS;
import com.mycompany.ecommerce.services.AuthService;
import com.mycompany.ecommerce.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	JWTUtils jwtUtils;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtils.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		// ---------- Exposing header authorization, for CORS
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

	// --------- @Valid @RequestBody get valid body from request
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO) {
		
		authService.sendNewPassword(emailDTO.getEmail());
		
		return ResponseEntity.noContent().build();
	}
	
}
