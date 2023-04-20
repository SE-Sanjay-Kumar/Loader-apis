package com.tms.loader.entities.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="payment")
public class Payment {
	@Id
	@Column(name="payment_id")
	private Integer paymentId;
	@Column(name="payment_mode")
	private String paymentMode;
}
