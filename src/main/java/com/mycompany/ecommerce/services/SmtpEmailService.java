package com.mycompany.ecommerce.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{
	
	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage simpleMailMessage) {
		LOG.info("Sending email ...");
		LOG.info(simpleMailMessage.toString());
		mailSender.send(simpleMailMessage);
		LOG.info("Email sent");
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		
		LOG.info("Sending email ...");
		LOG.info(msg.toString());
		javaMailSender.send(msg);
		LOG.info("Email sent");
		
	}

	
	
}
