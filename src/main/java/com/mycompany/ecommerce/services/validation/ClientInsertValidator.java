package com.mycompany.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.mycompany.ecommerce.domain.enums.EClientType;
import com.mycompany.ecommerce.dto.ClientNewDTO;
import com.mycompany.ecommerce.resources.exceptions.FieldMessage;
import com.mycompany.ecommerce.services.validation.utils.DocumentUtil;

// ---------- ConstraintValidator <ValidationClass, Destination Validation Class
public class ClientInsertValidator implements ConstraintValidator<ClientInsert, ClientNewDTO> {

	@Override
	public void initialize(ClientInsert ann) {

	}

	@Override
	public boolean isValid(ClientNewDTO clientNewDTO, ConstraintValidatorContext context) {

		// ---------- FieldMessage (custom error): fieldName and errorMessage
		List<FieldMessage> list = new ArrayList<>();

		if (clientNewDTO.getType() == null) {

			list.add(new FieldMessage("type", "Type is required"));

		}
		// ---------- PHYSICALPERSON
		else if (clientNewDTO.getType().equals(EClientType.PHYSICALPERSON.getCode())
				&& !DocumentUtil.isValidCPF(clientNewDTO.getDocument())) {

			list.add(new FieldMessage("document", "PHYSICALPERSON document is null and/or is invalid"));
			
		}
		// ---------- LEGALPERSON
		else if (clientNewDTO.getType().equals(EClientType.LEGALPERSON.getCode())
				&& !DocumentUtil.isValidCNPJ(clientNewDTO.getDocument())) {

			list.add(new FieldMessage("document", "LEGALPERSON document is null and/or is invalid"));
			
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
