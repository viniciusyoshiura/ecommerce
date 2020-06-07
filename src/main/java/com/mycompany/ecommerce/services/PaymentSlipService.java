package com.mycompany.ecommerce.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.PaymentSlip;

@Service
public class PaymentSlipService {

	public void insertPaymentSlip(PaymentSlip paymentSlip, Date dateOrder) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateOrder);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		
		paymentSlip.setDueDate(cal.getTime());
	}
	
}
