package com.mycompany.ecommerce.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError() {
		super();
	}

	public ValidationError(Integer status, String message, Long timeStamp) {
		super(status, message, timeStamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldMessage> fieldMessages) {
		this.errors = fieldMessages;
	}
	
	public void addError(String fieldName, String message) {
		
		errors.add(new FieldMessage(fieldName, message));
		
	}
}
