package com.tms.loader.payloads.driver;

import com.tms.loader.entities.Status;
import com.tms.loader.entities.vehicle.Freight;
import com.tms.loader.payloads.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DriverWithVehicleDto extends UserDto {
	private String licenseNumber;
	private int yearsOfExperience;
	private int salary;
	private int foodCost;
	private Status driverStatus;
	private Freight vehicle;

	
}
