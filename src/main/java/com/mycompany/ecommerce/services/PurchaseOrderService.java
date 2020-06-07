package com.mycompany.ecommerce.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.ItemPurchaseOrder;
import com.mycompany.ecommerce.domain.PaymentSlip;
import com.mycompany.ecommerce.domain.PurchaseOrder;
import com.mycompany.ecommerce.domain.enums.EPaymentStatus;
import com.mycompany.ecommerce.repositories.ItemPurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.PaymentRepository;
import com.mycompany.ecommerce.repositories.PurchaseOrderRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

// ---------- BLL Layer for PurchaseOrder
@Service
public class PurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository purchaseOrderRepository;
	
	@Autowired
	private PaymentSlipService paymentSlipService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ItemPurchaseOrderRepository itemPurchaseOrderRepository;
	
	@Autowired
	private ClientService clientService;
	
	public PurchaseOrder search(Integer id) {
		
		Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(id);
		
		return purchaseOrder.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + PurchaseOrder.class.getName()));
		
	}
	
	@Transactional
	public PurchaseOrder insert(PurchaseOrder purchaseOrder) {
		// --------- Assuring thas is a new object
		purchaseOrder.setId(null);
		purchaseOrder.setDateOrder(new Date());
		purchaseOrder.setClient(clientService.search(purchaseOrder.getClient().getId()));
		purchaseOrder.getPayment().setStatus(EPaymentStatus.PENDING);
		purchaseOrder.getPayment().setPurchaseOrder(purchaseOrder);
		
		if(purchaseOrder.getPayment() instanceof PaymentSlip) {
			
			PaymentSlip paymentSlip = (PaymentSlip) purchaseOrder.getPayment();
			
			// ---------- Setting dueDate of paymentSlip
			paymentSlipService.insertPaymentSlip(paymentSlip, purchaseOrder.getDateOrder());
			
		}
		
		purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
		paymentRepository.save(purchaseOrder.getPayment());
		
		for(ItemPurchaseOrder itemPurchaseOrder : purchaseOrder.getItemPurchaseOrders()) {
			
			itemPurchaseOrder.setDiscount(itemPurchaseOrder.getDiscount());
			itemPurchaseOrder.setProduct(productService.search(itemPurchaseOrder.getProduct().getId()));
			itemPurchaseOrder.setPrice(itemPurchaseOrder.getProduct().getPrice());
			itemPurchaseOrder.setPurchaseOrder(purchaseOrder);
		}
		
		itemPurchaseOrderRepository.saveAll(purchaseOrder.getItemPurchaseOrders());
		System.out.println(purchaseOrder);
		return purchaseOrder;
	}
	
}
