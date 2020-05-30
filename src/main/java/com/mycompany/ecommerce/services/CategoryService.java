package com.mycompany.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	// --------- Dependency will be automatically instantiated by Spring (dependency injection)
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category search(Integer id) {
		
		Optional<Category> category = categoryRepository.findById(id);
		//return category.orElse(null);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + Category.class.getName()));
	}
	
	public Category insert(Category category) {
		// ---------- Ensuring that category is a new object
		category.setId(null);
		return categoryRepository.save(category);
	
	}
	
}
