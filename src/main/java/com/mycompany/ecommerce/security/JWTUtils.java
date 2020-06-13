package com.mycompany.ecommerce.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
		return Jwts.builder().setSubject(username).setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();

	}

	public Boolean validToken(String token) {
		// ---------- Token claims
		// ---------- Claims that person is user and has token expiration
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			// ---------- now.before(expirationDate): now is before expirationDate
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				// ---------- Valid token
				return true;
			}
		}
		return false;
	}

	public String getUserName(String token) {
		// ---------- Token claims
		// ---------- Claims that person is user and has token expiration
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	private Claims getClaims(String token) {

		try {
			// ---------- Retrieving claims from token
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			// ---------- If some Exception happens return null
			return null;
		}

	}

}
