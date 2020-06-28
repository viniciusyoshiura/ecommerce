package com.mycompany.ecommerce.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.domain.PurchaseOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(PurchaseOrder purchaseOrder);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);
	
	void sendOrderConfirmationHtmlEmail(PurchaseOrder purchaseOrder);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNewPasswordEmail(Client client, String newPassword);
}
