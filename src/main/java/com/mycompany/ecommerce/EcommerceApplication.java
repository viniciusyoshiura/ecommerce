package com.mycompany.ecommerce;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.domain.Product;
import com.mycompany.ecommerce.repositories.CategoryRepository;
import com.mycompany.ecommerce.repositories.ProductRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	// ---------- Run when initializing application
	@Override
	public void run(String... args) throws Exception {
		
		Category category1 = new Category(null, "Computing");
		Category category2 = new Category(null, "Office");
		
		Product product1 = new Product(null, "Computer", 2000.00);
		Product product2 = new Product(null, "Printer", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);
		
		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2));
		
		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));
		
		categoryRepository.saveAll(Arrays.asList(category1, category2));
		productRepository.saveAll(Arrays.asList(product1, product2, product3));
		
	}

	
	
}
