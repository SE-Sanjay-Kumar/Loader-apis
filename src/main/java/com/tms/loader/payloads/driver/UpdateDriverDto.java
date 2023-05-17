package com.tms.loader.payloads.driver;

import com.tms.loader.entities.driver.DriverStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UpdateDriverDto {
	private String userName;
	private DriverStatus status;
	private String location;
	private int foodCost;
}
