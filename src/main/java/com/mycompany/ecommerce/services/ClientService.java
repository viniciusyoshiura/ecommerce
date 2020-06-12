package com.mycompany.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Address;
import com.mycompany.ecommerce.domain.City;
import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.domain.enums.EClientType;
import com.mycompany.ecommerce.dto.ClientDTO;
import com.mycompany.ecommerce.dto.ClientNewDTO;
import com.mycompany.ecommerce.repositories.AddressRepository;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.services.exceptions.DataIntegrityException;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;


// ---------- BLL Layer for client
@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Client search(Integer id) {

		Optional<Client> client = clientRepository.findById(id);
		// return client.orElse(null);
		return client.orElseThrow(
				() -> new ObjectNotFoundException("Objet not found! Id: " + id + ", Type: " + Client.class.getName()));
	}

	public Client insert(Client client) {
		// ---------- Ensuring that client is a new object
		client.setId(null);
		client = clientRepository.save(client);
		// ---------- Persisting addresses
		addressRepository.saveAll(client.getAddresses());
		return client;
	}

	public Client update(Client client) {
		// ---------- Check if clientRepository exists, otherwise throw exception
		Client newClient = search(client.getId());
		// ---------- Update newClient with client from database
		updateData(newClient, client);
		return clientRepository.save(newClient);

	}

	public void delete(Integer id) {
		// ---------- Check if client exists, otherwise throw exception
		search(id);
		try {

			clientRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException("It was not possible to delete since it has related entities");

		}
	}

	public List<Client> searchAll() {

		return clientRepository.findAll();

	}

	public Page<Client> searchWithPagination(Integer page, Integer size, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return clientRepository.findAll(pageRequest);
	}

	public Client fromDto(ClientDTO clientDto) {

		return new Client(clientDto.getId(), clientDto.getName(), clientDto.getEmail(), null, null, null);

	}

	public Client fromDto(ClientNewDTO clientNewDTO) {
		
		// ---------- Encoded password
		Client client = new Client(null, clientNewDTO.getName(), clientNewDTO.getEmail(), clientNewDTO.getDocument(),
				EClientType.toEnum(clientNewDTO.getType()), bCryptPasswordEncoder.encode(clientNewDTO.getPassword()));

		City city = new City(clientNewDTO.getCityId(), null, null);

		Address address = new Address(null, clientNewDTO.getStreet(), clientNewDTO.getNumber(),
				clientNewDTO.getComplement(), clientNewDTO.getDistrict(), clientNewDTO.getZipCode(), client, city);
		
		client.getAddresses().add(address);
		client.getPhones().add(clientNewDTO.getPhone1());

		if(clientNewDTO.getPhone2() != null) {
			client.getPhones().add(clientNewDTO.getPhone2());
		}
		
		if(clientNewDTO.getPhone3() != null) {
			client.getPhones().add(clientNewDTO.getPhone3());
		}
		
		return client;
	}

	private void updateData(Client newClient, Client client) {

		newClient.setName(client.getName());
		newClient.setEmail(client.getEmail());

	}
}
