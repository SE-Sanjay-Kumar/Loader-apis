package com.tms.loader.payloads.order;

import com.tms.loader.entities.Status;
import com.tms.loader.entities.driver.Driver;

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
	private Status status;
	private Driver driver;
	private float price;
}