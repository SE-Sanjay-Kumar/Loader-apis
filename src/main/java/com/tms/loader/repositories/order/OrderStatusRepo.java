package com.tms.loader.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.order.OrderStatus;

import jakarta.transaction.Transactional;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Integer> {
	
	@Transactional
	@Modifying
	@Query("update OrderStatus os set os.status = ?1 where os.statusId = ?2")
	int updateStatusById(String status,Integer id);
	
}
