package com.mycompany.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.PurchaseOrder;
import com.mycompany.ecommerce.repositories.PurchaseOrderRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	public PurchaseOrder search(Integer id) {
		
		Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(id);
		
		return purchaseOrder.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + PurchaseOrder.class.getName()));
		
	}
	
}
