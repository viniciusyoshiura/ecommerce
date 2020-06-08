package com.mycompany.ecommerce.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.mycompany.ecommerce.domain.PurchaseOrder;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(PurchaseOrder purchaseOrder) {
		
		SimpleMailMessage simpleMailMessage = prepareSimpleMailMessageFromPurchaseOrder(purchaseOrder);
		sendEmail(simpleMailMessage);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPurchaseOrder(PurchaseOrder purchaseOrder) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(purchaseOrder.getClient().getEmail());
		simpleMailMessage.setFrom(sender);
		simpleMailMessage.setSubject("Order confirmed! Code: " + purchaseOrder.getId());
		simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
		simpleMailMessage.setText(purchaseOrder.toString());
		
		return simpleMailMessage;
	}
	
}
