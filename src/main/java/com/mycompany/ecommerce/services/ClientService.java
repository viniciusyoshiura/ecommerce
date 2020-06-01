package com.mycompany.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.dto.ClientDTO;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.services.exceptions.DataIntegrityException;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public Client search(Integer id) {
		
		Optional<Client> client = clientRepository.findById(id);
		//return client.orElse(null);
		return client.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + Client.class.getName()));
	}
	
	public Client update(Client client) {
		// ---------- Check if clientRepository exists, otherwise throw exception
		Client newClient = search(client.getId());
		// ---------- Update newClient with client from database
		updateData(newClient, client);
		return clientRepository.save(newClient);
		
	}
	
	public void delete (Integer id) {
		// ---------- Check if client exists, otherwise throw exception
		search(id);
		try {
			
		clientRepository.deleteById(id);
		
		} catch(DataIntegrityViolationException e) {
		
			throw new DataIntegrityException("It was not possible to delete since it has related entities");
			
		}
	}
	
	public List<Client> searchAll(){
		
		return clientRepository.findAll();
		
	}
	
	public Page<Client> searchWithPagination(Integer page, Integer size, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction),
				orderBy);
		return clientRepository.findAll(pageRequest);
	}
	
	public Client fromDto(ClientDTO clientDto) {
		
		return new Client(clientDto.getId(), clientDto.getName(), clientDto.getEmail(), null, null);
		
	}
	
	private void updateData(Client newClient, Client client) {
		
		newClient.setName(client.getName());
		newClient.setEmail(client.getEmail());
		
	}
}
