package com.mycompany.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Integer>{

	
}
