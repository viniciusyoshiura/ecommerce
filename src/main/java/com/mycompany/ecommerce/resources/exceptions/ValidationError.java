package com.mycompany.ecommerce.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

// ---------- This class sets and gets list of errors (FieldMessage)
public class ValidationError extends StandardError{

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
}
