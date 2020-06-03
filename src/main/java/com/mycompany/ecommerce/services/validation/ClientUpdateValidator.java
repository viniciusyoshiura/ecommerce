package com.mycompany.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.dto.ClientDTO;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.resources.exceptions.FieldMessage;

// ---------- ConstraintValidator <ValidationClass, Destination Validation Class
public class ClientUpdateValidator implements ConstraintValidator<ClientUpdate, ClientDTO> {

	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public void initialize(ClientUpdate ann) {

	}

	@Override
	public boolean isValid(ClientDTO clientDTO, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Integer urlId = Integer.parseInt(map.get("id"));
		
		// ---------- FieldMessage (custom error): fieldName and errorMessage
		List<FieldMessage> list = new ArrayList<>();
		
		// ---------- Checking email
		Client aux = clientRepository.findByEmail(clientDTO.getEmail());
		if(aux != null && !aux.getId().equals(urlId)) {
			list.add(new FieldMessage("email", "Email already exists"));
		}
				
		
		// ---------- Include the tests here, by inserting errors on the list
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		// ---------- If list isEmpty, then there is no error
		return list.isEmpty();
	}
}
