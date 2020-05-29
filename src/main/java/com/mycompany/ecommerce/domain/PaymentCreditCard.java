package com.mycompany.ecommerce.domain;

import javax.persistence.Entity;


@Entity
public class PaymentCreditCard extends Payment {

	// --------- implements serializable not needed, since it inherits from super class
	private static final long serialVersionUID = 1L;
	
	private Integer installmentNumber;

	public PaymentCreditCard() {
		
	}

	public PaymentCreditCard(Integer id, Integer ePaymentState, PurchaseOrder order, Integer installmentNumber) {
		super(id, ePaymentState, order);
		this.installmentNumber = installmentNumber;
	}

	public Integer getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}
	
	
	
}
