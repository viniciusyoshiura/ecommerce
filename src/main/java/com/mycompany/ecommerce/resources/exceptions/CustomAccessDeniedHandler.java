package com.mycompany.ecommerce.resources.exceptions;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		response.setStatus(403);
		response.setContentType("application/json;charset=UTF-8\"");
		response.getWriter().append(json(request));
		
	}
	
	private String json(HttpServletRequest request) {
		long date = new Date().getTime();
		return "{\"timestamp\": " + date + ", " + "\"status\": 403, " + "\"error\": \"Forbidden\", "
				+ "\"message\": \"Access denied\", " + "\"path\": \"" + request.getRequestURL().toString() + "\" }";
	}
}
