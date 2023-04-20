package com.tms.loader.payloads.order;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDto {
	private Long orderId;
	private float totalWeight;
	private float totalSize;
	private boolean fragility;
	private Integer statusId;
	private float price;
	private Long clientId;
	private Long driverId;
	private Integer paymentId;
	private String pickUp;
	private String dropOff;
	private LocalDateTime schedule;
	
}
