package com.mycompany.ecommerce;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mycompany.ecommerce.domain.Category;
import com.mycompany.ecommerce.repositories.CategoryRepository;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner{

	@Autowired
	CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	// ---------- Run when initializing application
	@Override
	public void run(String... args) throws Exception {
		
		Category category1 = new Category(null, "Computing");
		Category category2 = new Category(null, "Office");
		
		categoryRepository.saveAll(Arrays.asList(category1, category2));
		
	}

	
	
}
