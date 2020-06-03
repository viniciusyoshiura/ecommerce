package com.mycompany.ecommerce.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.ecommerce.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	// ---------- readOnly: does not need to transact with database
	@Transactional(readOnly = true)
	Client findByEmail(String email);
	
}
