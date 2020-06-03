package com.mycompany.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.ecommerce.domain.Product;
import com.mycompany.ecommerce.dto.ProductDTO;
import com.mycompany.ecommerce.resources.utils.UrlUtils;
import com.mycompany.ecommerce.services.ProductService;

@RestController
@RequestMapping(value="/products")
public class ProductResource {

	@Autowired
	ProductService productService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Product product = productService.search(id);
		
		return ResponseEntity.ok(product);
		
	}
	
	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDTO>> findWithPaginaton(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "categories", defaultValue = "") String categories,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size,
			@RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {
		
		String decodedName = UrlUtils.decodeParam(name);
		List<Integer> ids = UrlUtils.decodeToListInteger(categories);
		Page<Product> products = productService.search(decodedName, ids, page, size, orderBy, direction);
		// ---------- Converting Product With pagination to ProductDTO
		Page<ProductDTO> productsDTO = products.map(obj -> new ProductDTO(obj));
		
		return ResponseEntity.ok(productsDTO);
		
	}
	
}
