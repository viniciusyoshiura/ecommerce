package com.mycompany.ecommerce.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.repositories.ClientRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		
		Client client = clientRepository.findByEmail(email);
		if(client == null) {
			throw new ObjectNotFoundException("Email not found");
		}
		
		// ---------- Updating client password
		String newPassword = newPassword();
		client.setPassword(bCryptPasswordEncoder.encode(newPassword));
		clientRepository.save(client);
		
		// ---------- Sending email with new password
		emailService.sendNewPasswordEmail(client, newPassword);
	}

	private String newPassword() {
		
		char[] arr = new char[10];
		for(int i = 0; i < 10; i++) {
			arr[i] = randomChar();
		}
		return new String(arr);
	}

	// ---------- Generate new password with 10 random characters 
	private char randomChar() {
		// --------- Unicode values: https://unicode-table.com/pt/
		// --------- Generate random int from 0 to 3
		int opt = random.nextInt(3);
		if(opt == 0) {
			// ---------- Generate digit
			// ---------- random.nextInt(10) : Generate random digit from 1 to 10
			// ---------- 48 is the unicode for 0, the first digit. See unicode table
			return (char) (random.nextInt(10) + 48);
		} else if(opt == 1) {
			// --------- Generate Upper case
			// ---------- random.nextInt(10) : Generate random digit from 1 to 26
			// ---------- 65 is the unicode for 'A', the first upper case. See unicode table
			return (char) (random.nextInt(26) + 65);
		} else {
			// --------- Generate Lower case
			// ---------- random.nextInt(10) : Generate random digit from 1 to 26
			// ---------- 97 is the unicode for 'a', the first lower case. See unicode table
			return (char) (random.nextInt(26) + 97);
		}
		
	}
	
	
	
}
