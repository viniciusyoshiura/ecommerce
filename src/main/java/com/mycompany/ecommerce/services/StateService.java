package com.mycompany.ecommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.domain.State;
import com.mycompany.ecommerce.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository stateRepository;
	
	public List<State> findAll() {
		return stateRepository.findAllByOrderByName();
	}
	
}
