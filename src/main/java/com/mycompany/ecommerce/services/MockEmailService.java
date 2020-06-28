package com.mycompany.ecommerce.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		
		LOG.info("Simulating email sending ...");
		LOG.info(simpleMailMessage.toString());
		LOG.info("Email sent");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		
		LOG.info("Simulating email sending HTML...");
		LOG.info(msg.toString());
		LOG.info("Email sent");
		
	}

	
	
}
