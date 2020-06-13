package com.mycompany.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.security.UserSS;

// ---------- This class implements UserDetailsService from Spring security
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ClientRepository clientRepository;
	
	// ---------- Find client and instantiate new UserSS
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Client client = clientRepository.findByEmail(email);
		
		if(client == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new UserSS(client.getId(), client.getEmail(), client.getPassword(), client.getProfiles());
	}

	
	
}
