package com.mycompany.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.ecommerce.domain.ItemPurchaseOrder;

@Repository
public interface ItemPurchaseOrderRepository extends JpaRepository<ItemPurchaseOrder, Integer>{

}
