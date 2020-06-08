package com.mycompany.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.SimpleMailMessage;


public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		
		LOG.info("Simulating email sending ...");
		LOG.info(simpleMailMessage.toString());
		LOG.info("Email sent");
	}

	
	
}
