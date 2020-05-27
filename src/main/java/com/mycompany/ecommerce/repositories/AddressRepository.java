package com.mycompany.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.domain.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{

}
