package com.mycompany.ecommerce.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// ---------- Component can be injected
@Component
public class JWTUtils {

	// ---------- Getting valeu from application.properties
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	public String generateToken(String username) {
	
		// ---------- Generating token from jsonwebtoken
		// ---------- Setting subject (username)
		// ---------- Setting expiration (from applicaton.properties)
		// ---------- Signing with selected SignatureAlgorithm and secret
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		
	}
	
}
