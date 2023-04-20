package com.tms.loader.payloads.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateOrderDto {
	private Long orderId;
	private Integer statusId;
	private Long driverId;
	private float price;
}