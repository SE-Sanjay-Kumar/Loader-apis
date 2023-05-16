package com.tms.loader.repositories.order;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.Client;
import com.tms.loader.entities.Status;
import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.order.Order;
import com.tms.loader.entities.order.OrderStatus;

import jakarta.transaction.Transactional;

public interface OrderRepo extends JpaRepository<Order, Long> {
	List<Order> findByClient(Client client);
	List<Order> findByStatus(Status status);
	
	@Transactional
	@Modifying
	@Query("update Order set price = ?1, status= ?2,estimatedArrivalOfGoods=?3 ,driver=?4, paymentStatus=?5 where orderId = ?6")
	int updateOrderById(float price, OrderStatus status,Date estimatedArrivalOfGoods, Driver driverId,String paymentStatus, Long id);

}
