package com.mycompany.ecommerce.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mycompany.ecommerce.domain.enums.EPaymentStatus;


//---------- JsonTypeName: which name to show in JSON. See Payment.class
@Entity
@JsonTypeName("paymentSlip")
public class PaymentSlip extends Payment {
	
	// --------- implements serializable not needed, since it inherits from super class
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dueDate;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date payday;
	
	public PaymentSlip() {
		
	}

	public PaymentSlip(Integer id, EPaymentStatus ePaymentStatus, PurchaseOrder order, Date dueDate, Date payday) {
		super(id, ePaymentStatus, order);
		this.dueDate = dueDate;
		this.payday = payday;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPayday() {
		return payday;
	}

	public void setPayday(Date payday) {
		this.payday = payday;
	}

	
	
}
