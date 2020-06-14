package com.mycompany.ecommerce.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mycompany.ecommerce.services.exceptions.AuthorizationException;
import com.mycompany.ecommerce.services.exceptions.DataIntegrityException;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

// ---------- Steps of customizing exceptions:
// ---------- Step 1: Create exception extending RuntimeException (see ObjectNotFoundException)
// ---------- Step 2: Create, if necessary, class to set values, messages etc. (see StandardError or ValidationError)
// ---------- Step 3: Create method ResourceExceptionHandler
// ---------- Step 4: Set standard error class and response 
@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(err);
	
	}
	
	// ---------- Occurs when deleting entities with other related entities, e. g.
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(err);
	
	}
	
	// ---------- Occurs when there are validation erros in synthatic data
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error", System.currentTimeMillis());
		
		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(err);
	
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		
		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(err);
	
	}
}
