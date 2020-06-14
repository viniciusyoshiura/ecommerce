package com.mycompany.ecommerce.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Client;
import com.mycompany.ecommerce.domain.ItemPurchaseOrder;
import com.mycompany.ecommerce.domain.PaymentSlip;
import com.mycompany.ecommerce.domain.PurchaseOrder;
import com.mycompany.ecommerce.domain.enums.EPaymentStatus;
import com.mycompany.ecommerce.repositories.ItemPurchaseOrderRepository;
import com.mycompany.ecommerce.repositories.PaymentRepository;
import com.mycompany.ecommerce.repositories.PurchaseOrderRepository;
import com.mycompany.ecommerce.security.UserSS;
import com.mycompany.ecommerce.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private EmailService emailService;
	
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
		
		// ---------- Normal email
		//emailService.sendOrderConfirmationEmail(purchaseOrder);
		
		// ---------- HTML email
		emailService.sendOrderConfirmationHtmlEmail(purchaseOrder);
		
		return purchaseOrder;
	}
	
	public Page<PurchaseOrder> searchWithPagination(Integer page, Integer size, String orderBy, String direction) {
	
		// ---------- Getting and checking user authenticated
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Access denied");
		}
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		
		Client client =  clientService.search(user.getId());
		return purchaseOrderRepository.findByClient(client, pageRequest);
	}
}
