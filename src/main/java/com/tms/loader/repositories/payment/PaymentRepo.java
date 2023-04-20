package com.tms.loader.repositories.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tms.loader.entities.payment.Payment;

import jakarta.transaction.Transactional;

public interface PaymentRepo extends JpaRepository<Payment, Integer> {
	@Transactional
	@Modifying
	@Query("update Payment pay set pay.paymentMode = ?1 where pay.paymentId = ?2")
	int updatePaymentModeById(String paymentMode, Integer id);
}
