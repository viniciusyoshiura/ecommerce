package com.mycompany.ecommerce.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mycompany.ecommerce.domain.PurchaseOrder;
import com.mycompany.ecommerce.services.PurchaseOrderService;

@RestController
@RequestMapping(value="/purchaseOrders")
public class PurchaseOrderResource {

	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		PurchaseOrder purchaseOrder = purchaseOrderService.search(id);
		
		return ResponseEntity.ok(purchaseOrder);
		
	}
	
	// ---------- @Valid - validate fields
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody PurchaseOrder purchaseOrder) {
		
		purchaseOrder = purchaseOrderService.insert(purchaseOrder);
		// ---------- Create URI for get REQUEST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(purchaseOrder.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
	public ResponseEntity<Page<PurchaseOrder>> findWithPaginaton(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size,
			@RequestParam(name = "orderBy", defaultValue = "dateOrder") String orderBy,
			@RequestParam(name = "direction", defaultValue = "DESC") String direction) {

		Page<PurchaseOrder> purchaseOrders = purchaseOrderService.searchWithPagination(page, size, orderBy, direction);
		
		return ResponseEntity.ok(purchaseOrders);
	}
}
