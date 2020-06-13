package com.mycompany.ecommerce.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.dto.CategoryDTO;
import com.mycompany.ecommerce.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Category category = categoryService.search(id);

		return ResponseEntity.ok(category);
	}

	// ---------- @Valid - validate categoryDTO fields
	// ---------- @PreAuthorize - only admin has access
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO categoryDto) {
		
		Category category = categoryService.fromDto(categoryDto);
		category = categoryService.insert(category);
		// ---------- Create URI for get REQUEST
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoryDTO categoryDto) {
		
		Category category = categoryService.fromDto(categoryDto);
		// ---------- Ensuring that object has id
		category.setId(id);
		category = categoryService.update(category);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		categoryService.delete(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> findAll() {

		List<Category> categories = categoryService.searchAll();
		// ---------- Converting Category to CategoryDTO
		List<CategoryDTO> categoriesDTO = categories.stream().map(obj -> new CategoryDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok(categoriesDTO);
	}

	@RequestMapping(value = "/pagination", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoryDTO>> findWithPaginaton(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size,
			@RequestParam(name = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction) {

		Page<Category> categories = categoryService.searchWithPagination(page, size, orderBy, direction);
		// ---------- Converting Category With pagination to CategoryDTO
		Page<CategoryDTO> categoriesDTO = categories.map(obj -> new CategoryDTO(obj));
		
		return ResponseEntity.ok(categoriesDTO);
	}

}
