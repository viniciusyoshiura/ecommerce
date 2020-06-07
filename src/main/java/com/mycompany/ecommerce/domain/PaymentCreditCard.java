package com.mycompany.ecommerce.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mycompany.ecommerce.domain.enums.EPaymentStatus;

// ---------- JsonTypeName: which name to show in JSON. See Payment.class
@Entity
@JsonTypeName("paymentCreditCard")
public class PaymentCreditCard extends Payment {

	// --------- implements serializable not needed, since it inherits from super class
	private static final long serialVersionUID = 1L;
	
	private Integer installmentNumber;

	public PaymentCreditCard() {
		
	}

	public PaymentCreditCard(Integer id, EPaymentStatus ePaymentStatus, PurchaseOrder order, Integer installmentNumber) {
		super(id, ePaymentStatus, order);
		this.installmentNumber = installmentNumber;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}
	
	
	
}
