package com.tms.loader.payloads.order;


import java.util.Date;

import com.tms.loader.entities.driver.Driver;
import com.tms.loader.entities.order.OrderStatus;

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
	private OrderStatus status;
	private Driver driver;
	private Date estimatedArrivalOfGoods;
	private float price;
	private String paymentStatus;
}