package com.mycompany.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	
	
}
