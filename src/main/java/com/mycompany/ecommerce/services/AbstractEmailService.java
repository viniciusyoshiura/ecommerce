package com.mycompany.ecommerce.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mycompany.ecommerce.domain.PurchaseOrder;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

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

	protected String htmlFromTemplatePurchaseOrder(PurchaseOrder purchaseOrder) {
		Context context = new Context();
		context.setVariable("purchaseOrder", purchaseOrder);
		return templateEngine.process("email/purchaseOrderConfirmation.html", context);
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(PurchaseOrder purchaseOrder) {
		try {
			MimeMessage mimeMessage = prepareMimeMessageFromPurchaseOrder(purchaseOrder);
			sendHtmlEmail(mimeMessage);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(purchaseOrder);
		}

	}

	protected MimeMessage prepareMimeMessageFromPurchaseOrder(PurchaseOrder purchaseOrder) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

		mimeMessageHelper.setTo(purchaseOrder.getClient().getEmail());
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setSubject("Order confirmed! Code: " + purchaseOrder.getId());
		mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
		mimeMessageHelper.setText(htmlFromTemplatePurchaseOrder(purchaseOrder), true);

		return mimeMessage;
	}
}
