package com.mycompany.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.dto.CategoryDTO;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.services.exceptions.DataIntegrityException;
import com.mycompany.ecommerce.services.exceptions.ObjectNotFoundException;

// --------- BLL Layer for Category
@Service
public class CategoryService {

	// --------- Dependency will be automatically instantiated by Spring (dependency
	// injection)
	@Autowired
	private CategoryRepository categoryRepository;

	public Category search(Integer id) {

		Optional<Category> category = categoryRepository.findById(id);
		// return category.orElse(null);
		return category.orElseThrow(() -> new ObjectNotFoundException(
				"Objet not found! Id: " + id + ", Type: " + Category.class.getName()));
	}

	public Category insert(Category category) {
		// ---------- Ensuring that category is a new object
		category.setId(null);
		return categoryRepository.save(category);

	}

	public Category update(Category category) {
		// ---------- Check if category exists, otherwise throw exception
		Category newCategory = search(category.getId());
		// ---------- Update newCategory with category from database
		updateData(newCategory, category);
		return categoryRepository.save(newCategory);

	}

	public void delete(Integer id) {
		// ---------- Check if category exists, otherwise throw exception
		search(id);
		try {

			categoryRepository.deleteById(id);

		} catch (DataIntegrityViolationException e) {

			throw new DataIntegrityException("It is not possible to delete categories that have products!");

		}
	}

	public List<Category> searchAll() {

		return categoryRepository.findAll();

	}

	public Page<Category> searchWithPagination(Integer page, Integer size, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return categoryRepository.findAll(pageRequest);
	}

	public Category fromDto(CategoryDTO categoryDto) {

		return new Category(categoryDto.getId(), categoryDto.getName());

	}

	private void updateData(Category newCategory, Category category) {

		newCategory.setName(category.getName());

	}
}
