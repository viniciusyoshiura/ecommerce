package com.mycompany.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

	// --------- Dependency will automatically instantiated by Spring (dependency injection)
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category search(Integer id) {
		
		Optional<Category> category = categoryRepository.findById(id);
		return category.orElse(null);
		
	}
	
}
