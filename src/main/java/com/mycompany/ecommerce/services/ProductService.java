package com.mycompany.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.domain.Product;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.repositories.ProductRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

//--------- BLL Layer for Product
@Service
public class ProductService {

	// --------- Dependency will be automatically instantiated by Spring (dependency
	// injection)
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product search(Integer id) {

		Optional<Product> category = productRepository.findById(id);
		// return category.orElse(null);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + Product.class.getName()));
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer size, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		
		List<Category> categories = categoryRepository.findAllById(ids);
		
//		return productRepository.search(name, categories, pageRequest);
		return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}
	
}
