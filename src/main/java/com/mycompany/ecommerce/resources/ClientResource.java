package com.mycompany.ecommerce.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.dto.ClientDTO;
import com.mycompany.ecommerce.dto.ClientNewDTO;
import com.mycompany.ecommerce.services.ClientService;

@RestController
@RequestMapping(value="/clients")
public class ClientResource {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Client client = clientService.search(id);
		
		return ResponseEntity.ok(client);
		
	}
	
	// ---------- @Valid - validate clientDTO fields
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClientNewDTO clientNewDTO) {
		
		Client client = clientService.fromDto(clientNewDTO);
		client = clientService.insert(client);
		// ---------- Create URI for get REQUEST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClientDTO clientDto) {
		
		Client client = clientService.fromDto(clientDto);
		// ---------- Ensuring that object has id
		client.setId(id);
		client = clientService.update(client);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		clientService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<ClientDTO>> findAll() {

		List<Client> clients = clientService.searchAll();
		// ---------- Converting Client to ClientDTO
		List<ClientDTO> clientsDTO = clients.stream().map(obj -> new ClientDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok(clientsDTO);
	}

	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
	public ResponseEntity<Page<ClientDTO>> findWithPaginaton(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size,
			@RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {

		Page<Client> clients = clientService.searchWithPagination(page, size, orderBy, direction);
		// ---------- Converting Client With pagination to ClientDTO
		Page<ClientDTO> clientsDto = clients.map(obj -> new ClientDTO(obj));
		
		return ResponseEntity.ok(clientsDto);
	}
	
	@RequestMapping(value="/image", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadImageProfile(@RequestParam(name = "file") MultipartFile multipartFile) {
		
		URI uri = clientService.uploadImageProfile(multipartFile);
		
		return ResponseEntity.created(uri).build();
	}
	
}
