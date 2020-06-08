package com.mycompany.ecommerce.services;

import org.springframework.mail.SimpleMailMessage;

import com.mycompany.ecommerce.domain.PurchaseOrder;

public interface EmailService {

	void sendOrderConfirmationEmail(PurchaseOrder purchaseOrder);
	
	void sendEmail(SimpleMailMessage simpleMailMessage);
	
}
