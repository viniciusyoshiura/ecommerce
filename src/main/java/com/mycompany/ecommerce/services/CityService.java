package com.mycompany.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.City;
import com.mycompany.ecommerce.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public List<City> findByState(Integer stateId) {
		return cityRepository.findCities(stateId);
	}
	
}
