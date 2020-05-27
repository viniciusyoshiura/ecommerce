package com.mycompany.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public Client search(Integer id) {
		
		Optional<Client> client = clientRepository.findById(id);
		//return category.orElse(null);
		return client.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + Client.class.getName()));
	}
	
}
